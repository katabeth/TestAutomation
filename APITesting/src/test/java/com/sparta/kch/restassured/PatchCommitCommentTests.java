package com.sparta.kch.restassured;

import com.sparta.kch.restassured.pojos.Comment;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class PatchCommitCommentTests {
    private static Response response;
    //private static JsonObject responseBody;
    private static Comment comment;
    private static final String PATCH_PATH = "/repos/{owner}/{repo}/commits/{commit_sha}/comments";
    private static final String DELETE_PATH = "/repos/{owner}/{repo}/comments/{comment_id}";

    private static final String COMMIT_ID = "f0722cfd773728c455f73bd796c65ac4e6665748";
    private static final String PATCH_MESSAGE = "Nice change";
    private static String originalMessage;
    private static Integer commentId; //set in beforeAll

    @BeforeAll
    public static void beforeAll() {
        originalMessage = Utils.getAllComments().as(Comment.class).getBody();
        // Make a PATCH request to the GitHub API to add a comment
        response = getPutResponse();
        comment = response.as(Comment.class);
        commentId = comment.getId();
        //responseBody = response.jsonPath().getJsonObject("body");
    }
    public static int getNumberOfComments() {
        return RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(
                        AppConfig.getBaseUri(),
                        AppConfig.getRepoPath(),
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
    public static Response getPutResponse() {
        return RestAssured
                .given(Utils.patchRequestSpecForComment(
                        AppConfig.getBaseUri(),
                        PATCH_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        COMMIT_ID,
                        PATCH_MESSAGE
                ))
                .when()
                .patch()
                .thenReturn();
    }
    @AfterAll
    public static void afterAll(){
        // Make a PUT request to the GitHub API to return to default
        RestAssured
                .given(Utils.patchRequestSpecForComment(
                        AppConfig.getBaseUri(),
                        PATCH_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        COMMIT_ID,
                        originalMessage
                ))
                .when()
                .patch();
    }
    @Test
    @DisplayName("Check status code is 200")
    public void checkStatusCode() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }
}
