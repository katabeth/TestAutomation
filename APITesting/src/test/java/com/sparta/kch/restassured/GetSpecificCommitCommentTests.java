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
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class GetSpecificCommitCommentTests {

    private static Response response;
    private static final String COMMENT_ID = "146183600";
    private static Comment comment;

    @BeforeAll
    public static void setup() {
        response = Utils.getSpecificComment(COMMENT_ID);
        comment = response.as(Comment.class);

        response.prettyPrint();
    }


    @Test
    @DisplayName("Get specific comment and check status code 200 returned")
    public void testStatusCode200() {
        MatcherAssert.assertThat(response.getStatusCode(), Matchers.is(200));
    }
    @Test
    @DisplayName("Get specific comment and check id is correct")
    public void testNumberOfComments() {
        MatcherAssert.assertThat(comment.getId(), Matchers.is(COMMENT_ID));
    }
    @Test
    @DisplayName("Get specific comment and check name is correct")
    public void testFirstCommenterName() {
        MatcherAssert.assertThat(comment.getUser().getLogin(), Matchers.is("katabeth"));
    }
    @Test
    @DisplayName("Get comment with a specific Id and check the Server header")
    void getCommentWithId_ChecksServerHeader() {
        MatcherAssert.assertThat(response.getHeader("Server"), Matchers.is("github.com"));
    }
    @Test
    @DisplayName("Get comment with a specific Id and check the reactions total count")
    void getCommentWithId_ChecksReactionsTotalCount() {
        MatcherAssert.assertThat(comment.getReactions().getTotalCount(), Matchers.is(0));
    }
    @Test
    @DisplayName("Comment createdAt Date/time is in the past")
    void getCommentWithId_ChecksCreatedAt() {
        MatcherAssert.assertThat(comment.createdDateInThePast(), Matchers.is(true));
    }

}
