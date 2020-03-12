# An Api Testing Framework Built Using Cucumber and RestAssured
## A file call application.yaml is required in
```src/test/resources/application.yaml```
```yaml
# base url for all the requests
baseURl: https://jsonplaceholder.typicode.com/todos
# content_type for 
content_type : "application/json"

```

## Logger Configuration is required for logging see log4j2 configuration in  [Apache](https://logging.apache.org/log4j/2.x/manual/configuration.html)
```yaml
src\main\resources\log4j2.xml
```
## All step definition have to extend BaseDefinition class

Example:
```java
@Log4j2
public class Demo extends BaseDefinition {

    @Given("path {string}")
    public void path(String path) {
        log.error("loging test");
        // basic request configured with basic url
        request.log().all();
        request.basePath(path);
    }
}

```
```java

    @When("user make {string} Request")
    public void userMakeRequest(String method) {
// call with http method 
        makeRequest(method);

    }
```

```java
 @Then("status code is {int}")
    public void statusCodeIs(int code) {
        // use then() from the framework to get validation 
        then().assertThat().statusCode(200);
 //or respond.assertThat() to get validation
    }
```
