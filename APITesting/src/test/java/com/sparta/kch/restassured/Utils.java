package com.sparta.kch.restassured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

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


}
