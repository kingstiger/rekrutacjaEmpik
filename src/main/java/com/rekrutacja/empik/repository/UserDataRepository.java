package com.rekrutacja.empik.repository;

import com.rekrutacja.empik.model.RequestCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<RequestCountEntity, String> {

}
