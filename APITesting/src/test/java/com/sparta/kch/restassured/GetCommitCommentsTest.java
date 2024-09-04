package com.sparta.kch.restassured;

import com.sparta.kch.restassured.pojos.Comment;
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

    private static Comment[] comments;

    @BeforeAll
    public static void setup() {
        String OWNER = AppConfig.getOwner();
        String REPO_NAME = AppConfig.getRepoName();
        String BEARER_TOKEN = AppConfig.getToken();
        String BASE_URL = AppConfig.getBaseUri();
        String PATH = AppConfig.getRepoPath();

        response = RestAssured
                .given(Utils.getGitHubCommentsRequestSpec(BASE_URL, PATH, BEARER_TOKEN, OWNER, REPO_NAME))
                .when()
                .get()
                .thenReturn();
        comments = response.as(Comment[].class);

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
    @Test
    @DisplayName("Get comment with a specific Id returns a comment with that Id")
    void getCommentWithId_ReturnsThatComment() {}

    @Test
    @DisplayName("Get comment with a specific Id and check the Server header")
    void getCommentWithId_ChecksServerHeader() {}

    @Test
    @DisplayName("Get comment with a specific Id and check the status code")
    void getCommentWithId_ChecksStatusCode() {}

    @Test
    @DisplayName("Get comment with a specific Id and check the reactions total count")
    void getCommentWithId_ChecksReactionsTotalCount() {}

    @Test
    @DisplayName("Get all repos comments returns 1 comment")
    void testNumberOfReposComments() {
        MatcherAssert.assertThat(comments.length, Matchers.is(1));
    }
}
