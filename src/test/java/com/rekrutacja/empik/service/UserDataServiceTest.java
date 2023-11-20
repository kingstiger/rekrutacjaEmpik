package com.rekrutacja.empik.service;

import com.rekrutacja.empik.connector.GitHubRestConnector;
import com.rekrutacja.empik.model.GitHubUser;
import com.rekrutacja.empik.model.RequestCountEntity;
import com.rekrutacja.empik.model.UserDataResponse;
import com.rekrutacja.empik.repository.UserDataRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.time.Instant;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserDataServiceTest {

    @Mock
    GitHubRestConnector gitHubRestConnector;
    @Mock
    UserDataRepository userDataRepository;

    UserDataService subject;

    @BeforeEach
    public void setup() {
        gitHubRestConnector = mock();
        userDataRepository = mock();
        subject = new UserDataService(gitHubRestConnector, userDataRepository);
    }

    @Test
    public void shouldReturnUserWithCalculation_whenEverythingOkAndNoPreviousRequests() {
        //given
        Double expectedCalculations = 18.0;
        when(gitHubRestConnector.getGitHubUser(sampleLogin())).thenReturn(sampleGitHubUser());
        when(userDataRepository.findById(sampleLogin())).thenReturn(Optional.empty());
        when(userDataRepository.save(any())).thenReturn(sampleRequestCountEntity());
        //when
        UserDataResponse actual = subject.processUserDataRequest(sampleLogin());
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getCalculations(), expectedCalculations);
    }

    @Test
    public void shouldReturnUserWithCalculation_whenEverythingOkAndWithPreviousRequests() {
        //given
        Double expectedCalculations = 18.0;
        when(gitHubRestConnector.getGitHubUser(sampleLogin())).thenReturn(sampleGitHubUser());
        when(userDataRepository.findById(sampleLogin())).thenReturn(Optional.of(sampleRequestCountEntity()));
        when(userDataRepository.save(any())).thenReturn(sampleRequestCountEntity());
        //when
        UserDataResponse actual = subject.processUserDataRequest(sampleLogin());
        //then
        Assertions.assertNotNull(actual);
        Assertions.assertEquals(actual.getCalculations(), expectedCalculations);
    }

    private RequestCountEntity sampleRequestCountEntity() {
        RequestCountEntity requestCountEntity = new RequestCountEntity();
        requestCountEntity.setLogin(sampleLogin());
        requestCountEntity.setRequestCount(sampleRequestCount());
        return requestCountEntity;
    }

    private long sampleRequestCount() {
        return 4L;
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
}