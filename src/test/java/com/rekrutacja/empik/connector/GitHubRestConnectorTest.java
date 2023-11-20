package com.rekrutacja.empik.connector;

import com.rekrutacja.empik.errorhandler.GitHubRestTemplateErrorHandler;
import com.rekrutacja.empik.errorhandler.GitHubServiceException;
import com.rekrutacja.empik.model.GitHubUser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
class GitHubRestConnectorTest {

    @Mock
    RestTemplate restTemplate;

    GitHubRestConnector subject;

    @BeforeEach
    public void setup() {
        subject = new GitHubRestConnector(restTemplate);
    }

    @Test
    public void shouldReturnGitHubUserData_whenEverythingOk() {
        //given
        when(restTemplate.getForEntity(sampleUriWithSampleLogin(), GitHubUser.class)).thenReturn(ResponseEntity.ofNullable(sampleGitHubUser()));
        //when
        GitHubUser actual = subject.getGitHubUser(sampleLogin());
        //then
        Assertions.assertNotNull(actual);
    }

    @Test
    public void shouldThrowGitHubServiceException_whenNoBody() {
        //given
        when(restTemplate.getForEntity(sampleUriWithSampleDifferentLogin(), GitHubUser.class)).thenReturn(ResponseEntity.ok().build());
        //then
        assertThrows(GitHubServiceException.class, () -> subject.getGitHubUser(sampleDifferentLogin()));
    }

    private String sampleUriWithSampleLogin() {
        return "https://api.github.com/users/" + sampleLogin();
    }

    private String sampleUriWithSampleDifferentLogin() {
        return "https://api.github.com/users/" + sampleDifferentLogin();
    }

    private GitHubUser sampleGitHubUser() {
        GitHubUser gitHubUser = new GitHubUser();
        gitHubUser.setId(sampleId());
        gitHubUser.setLogin(sampleLogin());
        gitHubUser.setType(sampleUserType());
        gitHubUser.setAvatarUrl(sampleAvatarUrl());
        gitHubUser.setCreatedAt(sampleCreatedAt());
        gitHubUser.setFollowers(sampleFollowers());
        gitHubUser.setPublicRepos(samplePublicRepos());
        return gitHubUser;
    }

    private int sampleId() {
        return 123123;
    }

    private String sampleUserType() {
        return "User";
    }

    private String sampleAvatarUrl() {
        return "https://avatars.githubusercontent.com/u/583231?v=4";
    }

    private Date sampleCreatedAt() {
        return Date.from(Instant.now());
    }

    private int samplePublicRepos() {
        return 4;
    }

    private int sampleFollowers() {
        return 2;
    }

    private String sampleLogin() {
        return "octocat";
    }
    private String sampleDifferentLogin() {
        return "aoctocat1";
    }
}