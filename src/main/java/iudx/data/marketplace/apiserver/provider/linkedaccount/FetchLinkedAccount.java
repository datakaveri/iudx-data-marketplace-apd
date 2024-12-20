package iudx.data.marketplace.apiserver.provider.linkedaccount;

import static iudx.data.marketplace.apiserver.provider.linkedaccount.util.Constants.*;
import static iudx.data.marketplace.apiserver.util.Constants.RESULTS;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import iudx.data.marketplace.common.Api;
import iudx.data.marketplace.common.HttpStatusCode;
import iudx.data.marketplace.common.RespBuilder;
import iudx.data.marketplace.common.ResponseUrn;
import iudx.data.marketplace.policies.User;
import iudx.data.marketplace.postgres.PostgresService;
import iudx.data.marketplace.razorpay.RazorPayService;
import org.apache.commons.lang.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FetchLinkedAccount {
  private static final Logger LOGGER = LogManager.getLogger(FetchLinkedAccount.class);
  PostgresService postgresService;
  Api api;
  RazorPayService razorPayService;
  String razorpayAccountProductId;
  String updatedAt;
  String createdAt;

  public FetchLinkedAccount(
      PostgresService postgresService, Api api, RazorPayService razorPayService) {
    this.postgresService = postgresService;
    this.api = api;
    this.razorPayService = razorPayService;
  }

  public Future<JsonObject> initiateFetchingLinkedAccount(User provider) {
    /*get accountId associated with the given provider ID*/
    Future<JsonObject> getAccountFuture = getAccountId(GET_ACCOUNT_ID_QUERY, provider);
    Future<JsonObject> accountDetailsFromRzpFuture =
        getAccountFuture.compose(
            accountFutureJson -> {
              String accountId = accountFutureJson.getString("accountId");
              return razorPayService.fetchLinkedAccount(accountId);
            });
    Future<JsonObject> successResponse =
        accountDetailsFromRzpFuture.compose(
            response -> {
              return generateSuccessResponse(response);
            });

    return successResponse;
  }

  public Future<JsonObject> generateSuccessResponse(JsonObject rzpResponseJson) {
    String emailId = rzpResponseJson.getString("email");
    String type = rzpResponseJson.getString("type");
    String status = rzpResponseJson.getString("status");
    String referenceId = rzpResponseJson.getString("reference_id");
    String category = rzpResponseJson.getJsonObject("profile").getString("category");
    String subcategory = rzpResponseJson.getJsonObject("profile").getString("subcategory");
    JsonObject registered =
        rzpResponseJson
            .getJsonObject("profile")
            .getJsonObject("addresses")
            .getJsonObject("registered");
    String street1 = registered.getString("street1");
    String street2 = registered.getString("street2");
    String city = registered.getString("city");
    String state = registered.getString("state");
    String postalCode = registered.getString("postal_code");
    String country = registered.getString("country");
    String contactName = rzpResponseJson.getString("contact_name");

    JsonObject registeredJson =
        new JsonObject()
            .put("street1", street1)
            .put("street2", street2)
            .put("city", city)
            .put("state", state)
            .put("postalCode", postalCode)
            .put("country", country);

    JsonObject addressJson = new JsonObject().put("registered", registeredJson);
    JsonObject profileJson =
        new JsonObject()
            .put("category", category)
            .put("subcategory", subcategory)
            .put("addresses", addressJson);

    JsonObject legalInfoJson = new JsonObject();
    /* checks if optional fields are null */
    if (rzpResponseJson.getJsonObject("legal_info") != null) {
      String pan = rzpResponseJson.getJsonObject("legal_info").getString("pan");
      if (StringUtils.isNotBlank(pan)) {
        legalInfoJson.put("pan", pan);
      }
      String gst = rzpResponseJson.getJsonObject("legal_info").getString("gst");
      if (StringUtils.isNotBlank(gst)) {
        legalInfoJson.put("gst", gst);
      }
    }
    String phoneNumber = rzpResponseJson.getString("phone");
    String accountId = rzpResponseJson.getString("id");
    JsonObject details = new JsonObject().put("accountId", accountId)
            .put("type", type)
            .put("status", status)
            .put("email", emailId)
            .put("accountProductId", getRazorpayAccountProductId())
            .put("profile", profileJson)
            .put("phone", phoneNumber);
    if (StringUtils.isNotBlank(contactName)) {
      details.put("contactName", contactName);
    }

    String businessType = rzpResponseJson.getString("business_type");
    String legalBusinessName = rzpResponseJson.getString("legal_business_name");

    details.put("referenceId", referenceId);
    details.put("businessType", businessType);
    details.put("legalBusinessName", legalBusinessName);

    String customerFacingBusinessName = rzpResponseJson.getString("customer_facing_business_name");

    if (StringUtils.isNotBlank(customerFacingBusinessName)) {
      details.put("customerFacingBusinessName", customerFacingBusinessName);
    }
    details.put("legalInfo", legalInfoJson);
    details.put("updatedAt", getUpdatedAt());
    details.put("createdAt", getCreatedAt());
    JsonObject response =
        new RespBuilder()
            .withType(ResponseUrn.SUCCESS_URN.getUrn())
            .withTitle(ResponseUrn.SUCCESS_URN.getMessage())
            .withResult(details)
            .getJsonResponse();

    return Future.succeededFuture(response);
  }

  Future<JsonObject> getAccountId(String query, User provider) {
    Promise<JsonObject> promise = Promise.promise();
    String providerId = provider.getUserId();
    String resourceServerUrl = provider.getResourceServerUrl();
    String finalQuery = query.replace("$1", providerId).replace("$2", resourceServerUrl);
    LOGGER.debug("Final query : " + finalQuery);
    postgresService.executeQuery(
        finalQuery,
        handler -> {
          if (handler.succeeded()) {
            if (!handler.result().getJsonArray(RESULTS).isEmpty()) {
              JsonObject result = handler.result().getJsonArray(RESULTS).getJsonObject(0);
              String accountProductId = result.getString("rzp_account_product_id");
              String updatedAt = result.getString("updatedAt");
              String createdAt = result.getString("createdAt");
              setRazorpayAccountProductId(accountProductId);
              setUpdatedAt(updatedAt);
              setCreatedAt(createdAt);
              String accountId = result.getString("account_id");
              promise.complete(new JsonObject().put("accountId", accountId));
            } else {
              promise.fail(
                  new RespBuilder()
                      .withType(HttpStatusCode.NOT_FOUND.getValue())
                      .withTitle(ResponseUrn.RESOURCE_NOT_FOUND_URN.getUrn())
                      .withDetail("Linked account cannot be fetched as, it is not found")
                      .getResponse());
            }

          } else {
            LOGGER.error("Failure : {}", handler.cause().getMessage());
            promise.fail(
                new RespBuilder()
                    .withType(HttpStatusCode.INTERNAL_SERVER_ERROR.getValue())
                    .withTitle(ResponseUrn.DB_ERROR_URN.getUrn())
                    .withDetail("Linked account cannot be fetched : Internal Server Error")
                    .getResponse());
          }
        });
    return promise.future();
  }

  public String getRazorpayAccountProductId() {
    return razorpayAccountProductId;
  }

  public void setRazorpayAccountProductId(String razorpayAccountProductId) {
    this.razorpayAccountProductId = razorpayAccountProductId;
  }

  public String getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(String updatedAt) {
    this.updatedAt = updatedAt;
  }

  public String getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(String createdAt) {
    this.createdAt = createdAt;
  }
}
