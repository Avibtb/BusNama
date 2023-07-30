package com.avinash.BusNama.repository;

import com.avinash.BusNama.model.CurrentUserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentUserSessionRepository extends JpaRepository<CurrentUserSession,Integer> {
    CurrentUserSession findByUuid(String uuid);
}
