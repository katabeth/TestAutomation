package com.sparta.kch.restassured;

import com.sparta.kch.restassured.pojos.Comment;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

public class PatchCommitCommentTests {
    private static Response response;
    //private static Comment comment;
    private static final String PATCH_PATH = "/repos/{owner}/{repo}/comments/{comment_id}";

    private static final String COMMENT_ID = "146183600";
    private static final String PATCH_MESSAGE = "Nice change";
    private static String originalMessage;

    @BeforeAll
    public static void beforeAll() {
        originalMessage = Utils.getSpecificComment(COMMENT_ID).jsonPath().getString("body");
        // Make a PATCH request to the GitHub API to add a comment
        response = getPutResponse();
        response.prettyPrint();
        //comment = response.as(Comment.class);
    }
    public static Response getPutResponse() {
        return RestAssured
                .given(Utils.patchRequestSpecForComment(
                        AppConfig.getBaseUri(),
                        PATCH_PATH,
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        COMMENT_ID,
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
                        COMMENT_ID,
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
