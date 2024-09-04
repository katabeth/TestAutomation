package com.sparta.kch.restassured;

import com.sparta.kch.restassured.pojos.Comment;
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
    //private static JsonObject responseBody;
    private static Comment comment;
    private static final String POST_PATH = "/repos/{owner}/{repo}/commits/{commit_sha}/comments";
    private static final String GET_ALL_PATH = "/repos/{owner}/{repo}/comments";
    private static final String DELETE_PATH = "/repos/{owner}/{repo}/comments/{comment_id}";

    private static final String COMMIT_ID = "f0722cfd773728c455f73bd796c65ac4e6665748";
    private static final String POST_MESSAGE = "Hello, World";

    private static Integer commentId; //set in beforeAll
    private static Integer initialNumberOfComments; //set in beforeAll
    private static Integer finalNumberOfComments; //set in beforeAll

    @BeforeAll
    public static void beforeAll() {
        initialNumberOfComments = RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(
                        AppConfig.getBaseUri(),
                        GET_ALL_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName()
                ))
                .when()
                .get()
                .jsonPath()
                .getList("id")
                .size();
        response = RestAssured
                .given(Utils.postRequestSpecForComment(
                        AppConfig.getBaseUri(),
                        POST_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        COMMIT_ID,
                        POST_MESSAGE
                ))
                .when()
                .post()
                .thenReturn();
        comment = response.as(Comment.class);
        commentId = comment.getId();
        //responseBody = response.jsonPath().getJsonObject("body");
        finalNumberOfComments = RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(
                        AppConfig.getBaseUri(),
                        GET_ALL_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName()
                ))
                .when()
                .get()
                .jsonPath()
                .getList("id")
                .size();
    }
    @AfterAll
    public static void afterAll(){
        // Make a DELETE request to the GitHub API
        RestAssured
                .given(Utils.deleteCommentRequestSpec(
                        AppConfig.getBaseUri(),
                        DELETE_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        commentId
                ))
                .when()
                .delete();
    }

    @Test
    @DisplayName("Validate the response body")
    void validateResponseBody() {
        MatcherAssert.assertThat(comment.getBody(), Matchers.is("Hello, World"));
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
