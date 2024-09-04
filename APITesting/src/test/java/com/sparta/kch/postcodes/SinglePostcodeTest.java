package com.sparta.kch.postcodes;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SinglePostcodeTest {

    private static Response response;

    @BeforeAll
    public static void setup() {
        response = RestAssured
                .given()
                    .baseUri("https://api.postcodes.io")
                    .basePath("/postcodes")
                    .header("Accept", "text/json")
                .when()
                    .get("/EC2Y5AS")
                    .thenReturn()
        ;
    }

    @Test
    @DisplayName("Status code 200 returned")
    public void testStatusCode200() {
        RestAssured
                .given()
                    .baseUri("https://api.postcodes.io")
                    .basePath("/postcodes")
                    .header("Accept", "text/json")
                    .log().all()
                .when()
                    .get("/EC2Y5AS")
                .then()
                    .log().all()
                    .assertThat()
                    .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Status code 200 returned v2")
    public void testStatusCode200_v2() {
        RestAssured
                .given()
                    .baseUri("https://api.postcodes.io/postcodes/EC2Y5AS")
                .when()
                    .get()
                .then()
                    .assertThat()
                    .statusCode(200)
        ;
    }

    @Test
    @DisplayName("Status code 200 returned v3")
    public void testStatusCode200_v3() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }

    @Test
    @DisplayName("Server name is in Header")
    public void testServerName() {
        MatcherAssert.assertThat(response.getHeader("Server"), Matchers.is("cloudflare"));
    }

    @Test
    @DisplayName("Primary Care Trust returned in response")
    public void testPrimaryCareTrust() {
        MatcherAssert.assertThat(response.getBody().jsonPath().getString("result.primary_care_trust"), Matchers.is("City and Hackney Teaching"));
    }

    @Test
    @DisplayName("Number of codes returned is 14")
    public void testNumberOfCodesReturned() {
        MatcherAssert.assertThat(response.getBody().jsonPath().getMap("result.codes").size(), Matchers.is(14));
    }
}
