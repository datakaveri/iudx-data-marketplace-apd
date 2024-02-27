package iudx.data.marketplace.consumer.util;

public class Constants {

  public static final String LIST_RESOURCES_QUERY =
      "select _id, resource_name, accessPolicy, provider_name from $0";
  public static final String LIST_PROVIDERS_QUERY =
      "select distinct provider_id, provider_name, resource_server from $0";

  public static final String LIST_PRODUCTS =
      "select pt.product_id as productId, pt.provider_name, "
          + "array_agg(json_build_object('id', rt._id, 'name', rt.resource_name)) as resources "
          + "from $0 as pt "
          + "inner join $9 as dpt on pt.product_id = dpt.product_id "
          + "inner join $8 as rt on dpt.resource_id = rt._id "
          + "where  pt.status=$1";

  public static final String GET_PRODUCT_VARIANT_INFO =
      "select pv._id, pv.product_variant_name, pv.product_id, pv.provider_id, pv.price, m.account_id "
          + "from $0 as pv inner join $9 as m on pv.provider_id = m.provider_id "
          + "where pv._id=$1 and pv.status=$2";

  public static final String INSERT_ORDER_QUERY =
      "insert into $0 (order_id, amount, currency, account_id, notes) values ('$1', $2, '$3', '$4', '$5')";

  public static final String INSERT_INVOICE_QUERY =
      "insert into $0 (_id, consumer_id, order_id, product_variant_id, payment_status, payment_time, expiry) "
          + "values ('$1', '$2', '$3', '$4', '$5', '$6', (select validity from $p where _id = '$4'))";

  public static final String TRANSFER_ID = "id";
  public static final String SOURCE = "source";
  public static final String AMOUNT = "amount";
  public static final String CURRENCY = "currency";
  public static final String INR = "INR";
  public static final String ACCOUNT_ID = "account_id";
  public static final String RECIPIENT = "recipient";
  public static final String NOTES = "notes";
  public static final String TRANSFERS = "transfers";
  public static final String TABLES = "tables";
  public static final String FETCH_ACTIVE_PRODUCT_VARIANTS  = "SELECT _id AS \"productVariantId\"," +
          " product_variant_name AS \"productVariantName\", \"product_id\" AS \"productId\",\n" +
          "provider_id AS \"providerId\", resource_name AS \"resourceName\", \n" +
          "resource_ids_and_capabilities AS \"resourceIdsAndCapabilities\",\n" +
          "price AS \"price\", validity AS \"expiryInMonths\"\n" +
          "FROM product_variant\n" +
          "WHERE product_id = '$1'\n" +
          "AND status = 'ACTIVE';";
}
