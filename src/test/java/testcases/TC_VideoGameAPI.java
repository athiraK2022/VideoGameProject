package testcases;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.testng.Assert.assertEquals;


import java.util.HashMap;
import java.lang.reflect.Array;

public class TC_VideoGameAPI {
	
	@Test(priority=1)
	
	public void getAllVideoGames() {
		
		String URL = "http://localhost:8080/app/videogames";
		
		given().when().get(URL).then().statusCode(200);
	}
	
	@Test(priority=2)
	
	public void createVideoGame() {
		
		String URL = "http://localhost:8080/app/videogames";
		
		HashMap hm = new HashMap();
		hm.put("id", "100");
		hm.put("name","Tom and Jerry");
		hm.put("releaseDate", "2022-02-26T07:08:32.187Z");
		hm.put("reviewScore","4");
		hm.put("category", "Kids");
		hm.put("rating", "Universal");
		
		RequestSpecification REQ_SPEC = given().contentType("application/json").body(hm);
		Response RESP = REQ_SPEC.when().baseUri(URL).post();
		ValidatableResponse VAL_RESP = RESP.then();
		VAL_RESP.statusCode(200);
		VAL_RESP.log().body();
		String responsebody = RESP.asString();
		Assert.assertEquals(responsebody.contains("Record Added Successfully"),true);
		
		}
	
	@Test(priority=3)
	
	public void getVideoGame() {
		
		String URL = "http://localhost:8080/app/videogames/10";
		
		RequestSpecification REQ_SPEC= given();
		Response RESP = REQ_SPEC.when().get(URL);
		ValidatableResponse VAL_RESP = RESP.then();
		VAL_RESP.statusCode(200);
		VAL_RESP.log().body();
		//String responsebody = RESP.body().asString();
		//String responseid = RESP.jsonPath().get("id");
		VAL_RESP.body("videoGame.id", equalTo("10"));
		VAL_RESP.body("videoGame.name",equalTo("Grand Theft Auto III"));
		
		
	}
	@Test(priority=4)
	
	public void updateVideoGame() {
		
		String URL ="http://localhost:8080/app/videogames/10";
		
		HashMap hm = new HashMap();
		hm.put("id", "10");
		hm.put("name","Tom and Jerry");
		hm.put("releaseDate", "2022-02-26T07:08:32.187Z");
		hm.put("reviewScore","4");
		hm.put("category", "Kids");
		hm.put("rating", "worldwide");
		
		
		RequestSpecification REQ_SPEC = given().contentType("application/json").body(hm);
		Response RESP = REQ_SPEC.when().put(URL);
		ValidatableResponse VAL_RESP = RESP.then();
		
		VAL_RESP.log().body();
		VAL_RESP.assertThat().body("videoGame.id", equalTo("10"));
		VAL_RESP.assertThat().body("videoGame.name", containsString("Tom"));
		
		}
	
	@Test(priority=5)
	
	public void deleteVideoGame() {
		
		String URL = "http://localhost:8080/app/videogames/100";
		
		RequestSpecification REQ_SPEC = given();
		Response RESP = REQ_SPEC.when().delete(URL);
		ValidatableResponse VAL_RESP = RESP.then();
		VAL_RESP.assertThat().statusCode(200);
		VAL_RESP.log().body();
		//String jsonresp = VAL_RESP.extract().body().asString();
		String json_resp = RESP.jsonPath().get("status").toString();
		Assert.assertEquals(json_resp.contains("Deleted"), true);
		
	}

}
