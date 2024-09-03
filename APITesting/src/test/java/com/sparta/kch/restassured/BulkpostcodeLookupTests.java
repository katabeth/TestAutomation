package com.sparta.kch.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

public class BulkpostcodeLookupTests {
    private static List<Object> responseMap;
    private static Response response;

    public static void setup() {
        String requestBody =
                "{ " +
                        "\"postcodes\": [ \"PR30SG\", \"M456GN\", \"EC2Y5AS\" ]" +
                        "}";
        response = RestAssured
                .given()
                .baseUri("https://api.postcodes.io")
                .basePath("/postcodes")
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post( )
                .thenReturn();
        responseMap = response.jsonPath().getList("result");
    }

    @Test
    @DisplayName("Bulk search for postcodes")
    public void testBulkSearch() {
        MatcherAssert.assertThat(responseMap.size(), Matchers.is(3));
    }
}
