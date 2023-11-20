package com.rekrutacja.empik.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.function.DoubleSupplier;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDataResponse {
    private Long id;
    private String login;
    private String name;
    private String type;
    private String avatarUrl;
    private Date createdAt;
    private Double calculations;

    public static UserDataResponse of(GitHubUser gitHubUser, DoubleSupplier calculationsFunction) {
        return UserDataResponse.builder()
                .id(gitHubUser.getId())
                .login(gitHubUser.getLogin())
                .name(gitHubUser.getName())
                .type(gitHubUser.getType())
                .avatarUrl(gitHubUser.getAvatarUrl())
                .createdAt(gitHubUser.getCreatedAt())
                .calculations(calculationsFunction.getAsDouble())
                .build();
    }
}
