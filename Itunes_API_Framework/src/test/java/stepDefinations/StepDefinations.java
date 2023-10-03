package stepDefinations;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import org.junit.Assert;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import resources.APIResources;
import resources.Utilities;

public class StepDefinations extends Utilities{
	RequestSpecification reqspec;
	Response response;
	String resourcepath;
	QueryableRequestSpecification queryRequest;
	String term;
	
	@Given("user has {string}")
	public void user_has(String APIName) {
	    //Get the API from API Resources
		APIResources resourceAPI=APIResources.valueOf(APIName);
		resourcepath = resourceAPI.getResource();
	}

	@Given("Query Param term as {string}")
	public void term_as(String Value) throws IOException {
		term = Value;
		Value = Value.replaceAll(" ", "+");
	
	  reqspec=given().spec(requestSpecification())
				.queryParam("term", Value);
	  
	}
	
	@Given("Query Params term as {string} and {string} as {string}")
	public void queryParams_as(String Value,String QueryParam2,String Value2) throws IOException {
		term = Value;
		Value = Value.replaceAll(" ", "+");
	
	  reqspec=given().spec(requestSpecification())
				.queryParam("term", Value)
				.queryParam(QueryParam2, Value2);
	  
	}
	
	@Given("Query Param term as {string} and {string} as {int}")
	public void queryParams_limit_as(String Value,String QueryParam2,Integer Value2) throws IOException {
		term = Value;
		Value = Value.replaceAll(" ", "+");
	
	  reqspec=given().spec(requestSpecification())
				.queryParam("term", Value)
				.queryParam(QueryParam2, Value2);
	  
	}

	@When("user hits iTunes Search API")
	public void user_hits_i_tunes_search_api() {

		response = reqspec.when().get(resourcepath);
		
	}
	
	@Then("the API call got response with status code {int}")
	public void the_api_call_got_response_with_status_code(Integer statusCode) {
	    
		response.then().assertThat().statusCode(statusCode);
	}
	@Then("each node in response contains given keyword")
	public void each_node_in_response_contains_given_keyword() {
		String responseNode;
		JsonPath j = new JsonPath(response.getBody().asString());

		for(int i=0; i<j.getInt("resultCount");i++) {
			responseNode = j.getString("results["+i+"]");
				Assert.assertTrue("Node "+(i+1)+" missing the search term", responseNode.contains(term));
		}
	}
	@Then("country in each node should be {string}")
	public void country_in_each_node_should_be(String expectedCountry) {

		String responseCountry;
		for(int i=0; i<response.getBody().jsonPath().getInt("resultCount");i++) {
			responseCountry = response.getBody().jsonPath().getString("results["+i+"].country");
			Assert.assertEquals(responseCountry, expectedCountry);
		}
	}
	
	@Then("response kind is {string}")
	public void response_kind_in_each_node_should_be(String expectedKind) {

		String responseKind;
		for(int i=0; i<response.getBody().jsonPath().getInt("resultCount");i++) {
			responseKind = response.getBody().jsonPath().getString("results["+i+"].kind");
			Assert.assertEquals(responseKind, expectedKind);
		}
	}
	
	@Then("response result count should be less than or equal to {int}")
	public void response_result_count_should_be_less_than_or_equal_to(Integer expectedResultCount) {
		int actualResultCount = response.getBody().jsonPath().getInt("resultCount");

		Assert.assertTrue(actualResultCount<=expectedResultCount);				
	    
	}
	
	@Then("JSONP validation should pass")
	public void jsonp_Validate() throws IOException {

		String rawRes = response.asString().trim();
		String jsonpstart = rawRes.substring(0,5);
		String jsonpend = rawRes.substring(rawRes.length()-2);
		String jsonP = jsonpstart + jsonpend.substring(0, jsonpend.length() - 1);

		assertEquals("JSONP Call FAIL!", "test()", jsonP);
	}
}
