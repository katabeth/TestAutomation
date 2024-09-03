package com.sparta.kch.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileReader;
import java.util.Map;
import java.util.Properties;

public class GetCommitCommentsTest {

    private static Response response;
    private static final String BASE_URL = "https://api.github.com/";
    private static final String PATH = "/repos/{owner}/{repo}/comments";
    static Properties prop = new Properties();


    @BeforeAll
    public static void setup() {
        try{
            prop.load(new FileInputStream("src/test/resources/git.properties"));
            String OWNER = prop.getProperty("OWNER");
            String REPO_NAME = prop.getProperty("REPO_NAME");
            String BEARER_TOKEN = prop.getProperty("BEARER_TOKEN");
            response = RestAssured
                    .given()
                    .baseUri(BASE_URL)
                    .basePath(PATH)
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    @DisplayName("Get all comments and check status code 200 returned")
    public void testStatusCode200() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }
    @Test
    @DisplayName("Get all repo comments returns one comment")
    public void testNumberOfComments() {
        MatcherAssert.assertThat(response.jsonPath().getList("").size(), Matchers.is(1));
    }
    @Test
    @DisplayName("Check name of first commenter is Katabeth")
    public void testFirstCommenterName() {
        MatcherAssert.assertThat(response.jsonPath().getString("[0].user.login"), Matchers.is("katabeth"));
    }
}
