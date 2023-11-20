package com.rekrutacja.empik.connector;

import com.rekrutacja.empik.errorhandler.GitHubServiceException;
import com.rekrutacja.empik.model.GitHubUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@RequiredArgsConstructor
public class GitHubRestConnector {

    private final RestTemplate restTemplate;
    private final static String GIT_HUB_API_URL = "https://api.github.com/";

    public GitHubUser getGitHubUser(String login) {
        String requestUrl = UriComponentsBuilder.fromUriString(GIT_HUB_API_URL)
                .pathSegment("users", login)
                .toUriString()
                .trim();

        ResponseEntity<GitHubUser> responseEntity = restTemplate.getForEntity(requestUrl, GitHubUser.class);
        HttpStatusCode statusCode = responseEntity.getStatusCode();

        if (statusCode.is2xxSuccessful() && responseEntity.hasBody()) {
            return responseEntity.getBody();
        } else {
            throw new GitHubServiceException();
        }
    }
}
