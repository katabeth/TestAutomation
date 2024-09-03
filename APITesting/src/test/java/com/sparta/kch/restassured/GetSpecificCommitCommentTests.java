package com.sparta.kch.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class GetSpecificCommitCommentTests {

    private static Response response;
    private static final String BASE_URL = "https://api.github.com/";
    private static final String PATH = "/repos/{owner}/{repo}/comments";
    private static final int COMMENT_ID = 146183600;
    private static Properties prop = new Properties();


    @BeforeAll
    public static void setup() {
        try {
            // load a properties file
            prop.load(new FileInputStream("src/test/resources/git.properties")); // note the leading /
            String OWNER = prop.getProperty("OWNER");
            String REPO_NAME = prop.getProperty("REPO_NAME");
            String BEARER_TOKEN = prop.getProperty("BEARER_TOKEN");
            response = RestAssured
                    .given()
                    .baseUri(BASE_URL)
                    .basePath(PATH+"/"+COMMENT_ID)
                    .headers(Map.of(
                            "Accept", "application/vnd.github+json",
                            "Authorization", "Bearer " + BEARER_TOKEN,
                            "X-Github-Api-Version", "2022-11-28"
                    ))
                    .pathParams("owner", OWNER,
                            "repo", REPO_NAME)
                    .when()
                    .get()
                    .thenReturn();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Test
    @DisplayName("Get specific comment and check status code 200 returned")
    public void testStatusCode200() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }
    @Test
    @DisplayName("Get specific comment and check id is correct")
    public void testNumberOfComments() {
        MatcherAssert.assertThat(response.jsonPath().getInt("id"), Matchers.is(COMMENT_ID));
    }
    @Test
    @DisplayName("Get specific comment and check name is correct")
    public void testFirstCommenterName() {
        MatcherAssert.assertThat(response.jsonPath().getString("user.login"), Matchers.is("katabeth"));
    }
}
