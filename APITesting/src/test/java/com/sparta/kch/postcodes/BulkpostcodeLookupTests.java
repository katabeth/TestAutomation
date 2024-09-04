package com.sparta.kch.postcodes;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BulkpostcodeLookupTests {
    private static List<Object> responseMap;
    private static Response response;

    @BeforeAll
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
                    .post()
                .thenReturn();
    }

    @Test
    @DisplayName("Bulk search for postcodes")
    public void testBulkSearch() {
        MatcherAssert.assertThat(response.jsonPath().getList("result").size(), Matchers.is(3));
    }
    @Test
    @DisplayName("Postcode data is returned correctly")
    public void testPostcodeData() {
        MatcherAssert.assertThat(
                response.jsonPath().getString("result[0].result.postcode"),
                Matchers.is("PR3 0SG")
        );
        MatcherAssert.assertThat(
                response.jsonPath().getString("result[0].result.country"),
                Matchers.is("England")
        );
    }
}
