package com.sparta.kch.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;

public class Utils {

    private static Properties prop = new Properties();
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
                .setBody(Map.of("body", commentBody))
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


}
