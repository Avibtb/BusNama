package com.avinash.BusNama.repository;

import com.avinash.BusNama.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation,Integer> {
}
