package com.sparta.kch.restassured;

import io.cucumber.cienvironment.internal.com.eclipsesource.json.JsonObject;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PostCommitCommentWithValidBodyTests {

    private static Response response;
    private static JsonObject responseBody;
    private static final String BASE_URI = "https://api.github.com";
    private static final String POST_PATH = "/repos/{owner}/{repo}/commits/{commit_sha}/comments";
    private static final String GET_ALL_PATH = "/repos/{owner}/{repo}/comments";
    private static final String DELETE_PATH = "/repos/{owner}/{repo}/comments/{comment_id}";

    private static final String COMMIT_ID = "<Add your commit sha>";
    private static final String POST_MESSAGE = "Hello, World";

    private static Integer commentId; //set in beforeAll
    private static Integer initialNumberOfComments; //set in beforeAll
    private static Integer finalNumberOfComments; //set in beforeAll

    @BeforeAll
    public static void beforeAll() {
        String [] properties = Utils.getProperties();
        String OWNER = properties[0];
        String REPO_NAME = properties[1];
        String BEARER_TOKEN = properties[2];

        initialNumberOfComments = RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(BASE_URI, GET_ALL_PATH, BEARER_TOKEN, OWNER, REPO_NAME))
                .when()
                .get()
                .jsonPath()
                .getList("id")
                .size();
        response = RestAssured
                .given(Utils.postRequestSpecForComment(BASE_URI, POST_PATH, BEARER_TOKEN, OWNER, REPO_NAME, COMMIT_ID, POST_MESSAGE))
                .when()
                .get()
                .thenReturn();
        commentId = response.jsonPath().getInt("id");
        responseBody = response.jsonPath().getJsonObject("body");

        finalNumberOfComments = initialNumberOfComments + 1;
    }
    @AfterAll
    public static void afterAll(){
        // Make a DELETE request to the GitHub API
    }

    @Test
    @DisplayName("Validate the response body")
    void validateResponseBody() {
        MatcherAssert.assertThat(response.jsonPath().getString("body"), Matchers.is("Hello, World"));
    }

    @Test
    @DisplayName("Validate the response status code")
    void validateResponseStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(201));
    }

    @Test
    @DisplayName("Validate the number of comments")
    void validateNumberOfComments() {
        MatcherAssert.assertThat(finalNumberOfComments, Matchers.is(initialNumberOfComments + 1));
    }





}
