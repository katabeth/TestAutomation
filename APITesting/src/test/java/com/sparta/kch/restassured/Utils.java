package com.sparta.kch.restassured;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class Utils {
    public static RequestSpecification getGitHubCommentsRequestSpec(String baseUri, String path, String token, String owner, String repo) {

        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("X-Github-Api-Version", "2022-11-28") // Unnecessary
                .addPathParam("owner", owner)
                .addPathParam("repo", repo)
                .build();
    }
    public static RequestSpecification getGitHubCommentsRequestSpec(String baseUri, String path, String token, String owner, String repo, String comment_id) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeader("Accept", "application/vnd.github+json")
                .addHeader("Authorization", "Bearer " + token)
                .addHeader("X-Github-Api-Version", "2022-11-28") // Unnecessary
                .addPathParam("owner", owner)
                .addPathParam("repo", repo)
                .addPathParam("comment_id", comment_id)
                .build();
    }
    public static RequestSpecification postRequestSpecForComment(String baseUri, String path, String token, String owner, String repo, String commitSha, String commentBody) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "commit_sha", commitSha
                ))
                .setContentType(ContentType.JSON)
                .setBody(Map.of(
                        "body", commentBody
                ))
                .build();
    }
    public static RequestSpecification deleteCommentRequestSpec(String baseUri, String path, String token, String owner, String repo, Integer commentId) {
        return new RequestSpecBuilder()
                .setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "comment_id", commentId
                ))
                .build();
    }
    public static RequestSpecification patchRequestSpecForComment(String baseUri, String path, String token, String owner, String repo, String commentId, String message) {
        return new RequestSpecBuilder().setBaseUri(baseUri)
                .setBasePath(path)
                .addHeaders(Map.of(
                        "Accept", "application/vnd.github+json",
                        "Authorization", "Bearer " + token
                ))
                .addPathParams(Map.of(
                        "owner", owner,
                        "repo", repo,
                        "comment_id", commentId
                ))
                .setContentType(ContentType.JSON)
                .setBody(Map.of("body", message))
                .build();
    }
    public static Response getAllComments() {
        return RestAssured
                .given(getGitHubCommentsRequestSpec(
                        AppConfig.getBaseUri(),
                        AppConfig.getRepoPath(),
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName()
                ))
                .when()
                .get()
                .thenReturn();
    }
    public static Response getSpecificComment(String commentId) {
        return RestAssured
                .given(getGitHubCommentsRequestSpec(
                        AppConfig.getBaseUri(),
                        AppConfig.getUniquePath(),
                        AppConfig.getToken(),
                        AppConfig.getOwner(),
                        AppConfig.getRepoName(),
                        commentId
                ))
                .when()
                .get()
                .thenReturn();
    }

}
