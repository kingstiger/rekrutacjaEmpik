package com.rekrutacja.empik.service;

import com.rekrutacja.empik.connector.GitHubRestConnector;
import com.rekrutacja.empik.errorhandler.CalculationInfiniteException;
import com.rekrutacja.empik.model.GitHubUser;
import com.rekrutacja.empik.model.RequestCountEntity;
import com.rekrutacja.empik.model.UserDataResponse;
import com.rekrutacja.empik.repository.UserDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDataService {

    private final GitHubRestConnector gitHubRestConnector;
    private final UserDataRepository userDataRepository;

    public Optional<RequestCountEntity> getCount(String login) {
        return userDataRepository.findById(login);
    }

    public UserDataResponse processUserDataRequest(String login) {
        saveOrUpdateRequestCount(login);

        GitHubUser gitHubUser = gitHubRestConnector.getGitHubUser(login);
        return UserDataResponse.of(gitHubUser, () -> doCalculations(gitHubUser));
    }

    private void saveOrUpdateRequestCount(String login) {
        Optional<RequestCountEntity> loginRequestCount = userDataRepository.findById(login);
        RequestCountEntity requestCountEntity;

        if(loginRequestCount.isPresent()) {
            requestCountEntity = loginRequestCount.get();
            Long requestCount = requestCountEntity.getRequestCount();
            requestCountEntity.setRequestCount(requestCount + 1);
        } else {
            requestCountEntity = new RequestCountEntity();
            requestCountEntity.setLogin(login);
            requestCountEntity.setRequestCount(1L);
        }

        userDataRepository.save(requestCountEntity);
    }

    private Double doCalculations(GitHubUser gitHubUser) {
        Double result = 6.0 / gitHubUser.getFollowers() * (2 + gitHubUser.getPublicRepos());
        if(result.isInfinite()) {
            throw new CalculationInfiniteException();
        }
        return result;
    }
}
