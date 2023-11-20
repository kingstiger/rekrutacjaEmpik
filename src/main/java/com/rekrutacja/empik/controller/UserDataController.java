package com.rekrutacja.empik.controller;

import com.rekrutacja.empik.errorhandler.WrongLoginException;
import com.rekrutacja.empik.model.RequestCountEntity;
import com.rekrutacja.empik.model.UserDataResponse;
import com.rekrutacja.empik.service.UserDataService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("users")
@RequiredArgsConstructor
public class UserDataController {

    private final UserDataService userDataService;

    @GetMapping("/{login}")
    public ResponseEntity<UserDataResponse> getUserData(@PathVariable("login") String login) {
        if(login == null || login.isEmpty()) {
            throw new WrongLoginException("No login was provided");
        }

        return ResponseEntity.ofNullable(userDataService.processUserDataRequest(login));
    }

    @GetMapping("/count/{login}")
    public ResponseEntity<RequestCountEntity> getRequestAmount(@PathVariable("login") String login) {
        if(login == null || login.isEmpty()) {
            throw new WrongLoginException("No login was provided");
        }
        return ResponseEntity.of(userDataService.getCount(login));
    }
}
