package com.rekrutacja.empik.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.hibernate.Hibernate;

import java.util.Objects;

@Entity(name = "RequestCount")
@Getter
@Setter
@ToString
public class RequestCountEntity {
    @Id
    private String login;
    private Long requestCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        RequestCountEntity that = (RequestCountEntity) o;
        return login != null && Objects.equals(login, that.login);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
