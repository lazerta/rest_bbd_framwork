package base;

import io.cucumber.java.Before;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.LogConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.io.IoBuilder;


import java.io.File;
import java.io.PrintStream;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class BaseDefinition {
    private static final Logger log = LogManager.getLogger(BaseDefinition.class);
    private static RequestSpecBuilder REQUEST_BUILDER = new RequestSpecBuilder();
    private static RequestSpecification REQUEST;
    public static final SessionFilter SESSION = new SessionFilter();
    private static PrintStream logStream = IoBuilder.forLogger(log).buildPrintStream();
    private static LogConfig logConfig = RestAssuredConfig.config().getLogConfig();
    protected RequestSpecification request;
    private static Config config = Config.getInstance();
    protected Response response;
    private static final String JSON_DATA_PATH =
            Config.getInstance().getData_path() + "jsons" + File.separator + "data" + File.separator;
    private static final String JSON_Schema_PATH =
            Config.getInstance().getData_path() + "jsons" + File.separator + "schema" + File.separator;

    public BaseDefinition() {
        setUp();

    }

    /**
     * redirect rest assured log to log4j2
     * set basic url from application.yaml
     *
     * @see Config for all configuration options
     */
    public void setUp() {
        logConfig.defaultStream(logStream)
                .enablePrettyPrinting(true)
                .enableLoggingOfRequestAndResponseIfValidationFails();
        REQUEST_BUILDER.addFilter(SESSION).
                setContentType(Config.getInstance().getContent_type())
                .setBaseUri(config.getBaseURl())
                .setAccept(ContentType.JSON);
        REQUEST = REQUEST_BUILDER.build();
        request = given().spec(REQUEST);


    }

    /**
     * @return ValidatableResponse
     * @see Response#then()
     */
    public ValidatableResponse then() {
        return response.then();
    }

    /**
     * @param path path of the request
     * @see RequestSpecification#basePath(String)
     */
    public void path(String path) {
        request.basePath(path);
    }

    /**
     * @param json String representation of a json file
     * @return A jsonPath representing the json file
     */
    public static JsonPath parseJson(String json) {
        return new JsonPath(json);
    }

    /**
     * @param json     String representation of a json file
     * @param rootPath base rootPath;
     * @return A jsonPath pointing to root whose root is {rootPath} representing the json file
     * @see JsonPath#setRootPath(String)
     */
    public JsonPath parseJson(String json, String rootPath) {
        JsonPath path1 = new JsonPath(json);
        return path1.setRootPath(rootPath);
    }

    /**
     * @param file a file representing underling json file
     * @return a json path of the file
     */
    public JsonPath readJson(File file) {
        return new JsonPath(file);
    }

    /**
     * @param relativePath a file path relative tp /test/resource/jsons/data
     * @return a json path of the file
     */
    public JsonPath readJson(String relativePath) {
        return new JsonPath(JSON_DATA_PATH + relativePath);
    }

    /**
     * @param method a case insensitive string represent https method
     */
    public void makeRequest(String method) {

        if (method.equalsIgnoreCase("get")) {
            response = request.get();
            return;
        }
        if (method.equalsIgnoreCase("post")) {
            response = request.post();
            return;

        }
        if (method.equalsIgnoreCase("put")) {
            response = request.put();
            return;

        }
        if (method.equalsIgnoreCase("delete")) {
            response = request.delete();

        }
        throw new IllegalArgumentException("Method: " + method + " is not valid");
    }

    public void withQueryParams(Map<String, String> map) {
        request.queryParams(map);
    }

    public void withQueryParam(String key, String value) {
        request.queryParam(key, value);
    }

    /**
     * validate if returning json match the schema point by the file
     * @param file file
     */
    public void hasValidSchemaAS(File file) {
        response.then().assertThat().body(matchesJsonSchema(file));
    }

    /**
     * validate if returning json match the schema point by the file
     * @param file a file path relative to /test/resource/jsons/schema
     */
    public void hasValidSchemaAS(String file) {
        response.then().assertThat().body(matchesJsonSchema(JSON_Schema_PATH+file));
    }
}
