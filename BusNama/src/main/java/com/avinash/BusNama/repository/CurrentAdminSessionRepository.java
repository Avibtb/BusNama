package com.avinash.BusNama.repository;

import com.avinash.BusNama.model.CurrentAdminSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrentAdminSessionRepository extends JpaRepository<CurrentAdminSession,Integer> {
   @Query("select c from CurrentAdminSession c where c.aid=?1")
   CurrentAdminSession findByaid(String aid);
}
