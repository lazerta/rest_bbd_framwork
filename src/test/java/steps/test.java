package steps;

import base.BaseDefinition;


import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
@Log4j2
public class test extends BaseDefinition {

    @Given("path {string}")
    public void path(String path) {
        log.error("loging test");
        request.log().all();
        request.basePath(path);
    }


    @When("user make {string} Request")
    public void userMakeRequest(String method) {
        makeRequest(method);

    }

    @Then("status code is {int}")
    public void statusCodeIs(int code) {
        then().assertThat().statusCode(200);
    }



    @Given("path {string} with query param as following:")
    public void pathWithQueryParamAsFollowing(String path,Map<String,String> map) {

    }
}
