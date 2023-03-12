package com.juaracoding.restassured;

import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestApiUser {

    int idMovie= 315162;
    String apiKey = "63c1bb6876bbe4f1d21c428d73cc8136";

    String baseUrl = "https://api.themoviedb.org/3/movie/";

    String authToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI2M2MxYmI2ODc2YmJlNGYxZDIxYzQyOGQ3M2NjODEzNiIsInN1YiI6IjY0MGM5N2RlMzIzZWJhMDA4NDQ5MzA4MCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.lH7_Sb4K9kQW4_nJup_gmx9MFLWyCfP0WPOShUnwdWo";
    String endpointPlaying = (baseUrl+ "/now_playing?api_key=" + apiKey+ "&language=en-US&page=1");

    String endpointPopular = (baseUrl+ "/popular?api_key=" + apiKey+ "&language=en-US&page=2");

    String endpointRating = ("https://api.themoviedb.org/3/movie/ "+ idMovie + "/rating");
    @Test
    public void testGetPlaying () {
        Response response = RestAssured.get(endpointPlaying);
        System.out.println("Respon Body = "+response.getBody().asString());
        System.out.println("Status Code =" +response.getStatusCode());
        System.out.println("Time ="+response.getTime());
        System.out.println("Header =" + response.getHeader("content-type"));
        Assert.assertEquals(response.statusCode(), 200);

    }

    @Test
    public void testGetMoviePopular () {
        given().get(endpointPopular)
                .then()
                .statusCode(200)
                .body("page", equalTo(2))
                .log().all();
    }

    @Test
    public void testCreateUser () {
        JSONObject request = new JSONObject();
        request.put("value",8.5);
        System.out.println(request);

        given()
                .header("Content-Type", "application/json")
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("Authorization", authToken)
                .body(request.toJSONString())
                .when()
                .post(endpointRating)
                .then()
                .statusCode(201)
                .body("success", equalTo(true))
                .log().all();

    }
}
