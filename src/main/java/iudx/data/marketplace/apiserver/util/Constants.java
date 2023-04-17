package iudx.data.marketplace.apiserver.util;

import java.util.regex.Pattern;

public class Constants {

  public static final String ID = "id";
  public static final String API_ENDPOINT = "apiEndpoint";
  public static final String API_METHOD = "method";
  public static final String USER_ID = "userid";
  public static final String EXPIRY = "expiry";
  public static final String IID = "iid";
  public static final String RESULTS = "results";
  public static final String METHOD = "method";
  public static final String STATUS = "title";
  public static final String TYPE_FAIL = "fail";
  public static final String MSG_BAD_QUERY = "Bad query";

  // Header params
  public static final String HEADER_TOKEN = "token";
  public static final String HEADER_HOST = "Host";
  public static final String HEADER_ACCEPT = "Accept";
  public static final String HEADER_CONTENT_LENGTH = "Content-Length";
  public static final String HEADER_CONTENT_TYPE = "Content-Type";
  public static final String HEADER_ORIGIN = "Origin";
  public static final String HEADER_REFERER = "Referer";
  public static final String HEADER_ALLOW_ORIGIN = "Access-Control-Allow-Origin";


  // request/response params
  public static final String CONTENT_TYPE = "content-type";
  public static final String APPLICATION_JSON = "application/json";
  // json fields
  public static final String JSON_TYPE = "type";
  public static final String JSON_TITLE = "title";
  public static final String JSON_DETAIL = "detail";

  // paths
  public static final String PROVIDER_BASE_PATH = "/provider";
  public static final String PRODUCT_PATH = "/product";
  public static final String PRODUCT_VARIANT_PATH = "/product-variant";
  public static final String LIST_PRODUCTS_PATH = "/list/products";
  public static final String LIST_PURCHASES_PATH = "/list/purchases";
  public static final String CONSUMER_BASE_PATH = "/consumer";
  public static final String LIST_PROVIDERS_PATH = "/list/providers";
  public static final String LIST_DATASETS_PATH = "/list/datasets";
  public static final String USERMAPS_PATH = "/product/usermaps";
  public static final String VERIFY_PATH = "/verify";

  // query parameters | request body

  public static final String PRODUCT_ID = "id";
  public static final String PRODUCT_VARIANT_NAME = "variant";
  public static final String DATASET_ID = "datasetID";

  public static final String PROVIDER_ID = "providerID";

  //validations
  public static final int VALIDATION_URN_DELIMITER_COUNT = 3;

  public static final int VALIDATION_PRODUCT_ID_MAXLEN = 150;
  public static final Pattern VALIDATION_PRODUCT_ID_REGEX = Pattern.compile("^[a-zA-Z0-9/.:-]{3,150}$");
  public static final int VALIDATION_VARIANT_NAME_MAX_LEN = 100;
  public static final Pattern VALIDATION_VARIANT_NAME_REGEX = Pattern.compile("^[a-zA-Z0-9-]{3,100}$");
  public static final int VALIDATION_DATASET_ID_MAXLEN = 512;
  public static final Pattern VALIDATION_DATASET_ID_REGEX = Pattern.compile("^[a-zA-Z0-9.]{4,100}/[a-zA-Z0-9.]{4,100}/[a-zA-Z.]{4,100}/[a-zA-Z-_.]{4,100}$");
}
