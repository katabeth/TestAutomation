package com.sparta.kch.restassured;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SinglePostcodeTest {

    @Test
    @DisplayName("Status code 200 returned")
    public void testStatusCode() {
        RestAssured
                .given()
                    .baseUri("https://api.postcodes.io")
                    .basePath("/postcodes")
                .when()
                    .get("/EC2Y5AS")
                .then()
                    .assertThat()
                    .statusCode(200)
                ;
    }
}
