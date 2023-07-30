package com.avinash.BusNama.repository;

import com.avinash.BusNama.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
public User findByEmail(String email);

}
