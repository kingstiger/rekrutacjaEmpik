package com.rekrutacja.empik.errorhandler;

import org.springframework.http.HttpStatusCode;

public class GitHubServiceException extends RuntimeException {

    public GitHubServiceException(HttpStatusCode code) {
        super(String.format("Received code %s from GitHub", code));
    }

    public GitHubServiceException() {
        super("Unknown error or no body found on response from GitHub");
    }
}
