package rest.assured.testscripts;

import java.util.HashMap;
import java.util.UUID;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class Ceate_Update_Delete_Resource {
	
	UUID uuid = UUID.randomUUID();
	HashMap<String, String> map = new HashMap<>();
	int id;
	
	@BeforeTest
	public void createPayload() {
		map.put("name", "Jason");
		map.put("email", uuid+"@yahoo.com");
		map.put("gender", "male");
		map.put("status", "active");
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "public/v2/users";
	}
	
	@Test(priority=0)
	public void createResource() {
		Response response = RestAssured
			.given()
				.contentType("application/json")
				.body(map)
				.header("Authorization", "Bearer eb8b9b5d31c1e1d1bead41d0d7fa0bf7ef71d879421259ea9d26ac57599810b4")
			.when()
				.post()
			.then()
				.statusCode(201)
				.log().all()
				.contentType(ContentType.JSON).extract().response();
		
		JsonPath jsonPath = response.jsonPath();
		id = jsonPath.get("id");
	}

	@Test (priority=1)
	public void verifyResourceExistence() {
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "/public/v2/users/" + id;
		
		RestAssured
			.given()
				.contentType("application/json")
				.header("Authorization", "Bearer eb8b9b5d31c1e1d1bead41d0d7fa0bf7ef71d879421259ea9d26ac57599810b4")
			.when()
				.get()
			.then()
				.statusCode(200)
				.log().all();
	}
	
	@Test (priority=2)
	public void udateResource() {
		map.put("name", "Jason Bourne");
		map.put("email", uuid+"@yahoo.com");
		map.put("gender", "male");
		map.put("status", "active");
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "public/v2/users/" + id;
		
		RestAssured
			.given()
				.contentType("application/json")
				.body(map)
				.header("Authorization", "Bearer eb8b9b5d31c1e1d1bead41d0d7fa0bf7ef71d879421259ea9d26ac57599810b4")
			.when()
				.put()
			.then()
				.statusCode(200)
				.log().all();
	}
	
	@Test (priority=3)
	public void deleteResource() {
		RestAssured.baseURI = "https://gorest.co.in/";
		RestAssured.basePath = "public/v2/users/" + id;
		
		RestAssured
			.given()
				.contentType("application/json")
				.header("Authorization", "Bearer eb8b9b5d31c1e1d1bead41d0d7fa0bf7ef71d879421259ea9d26ac57599810b4")
			.when()
				.delete()
			.then()
				.statusCode(204);
		
	}
}
