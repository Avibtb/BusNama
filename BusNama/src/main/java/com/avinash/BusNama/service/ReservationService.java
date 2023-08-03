package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.ReservationException;
import com.avinash.BusNama.model.DTO.ReservationDTO;
import com.avinash.BusNama.model.Reservation;
import jakarta.persistence.criteria.CriteriaBuilder;

import java.util.List;

public interface ReservationService {
    Reservation addReservation(ReservationDTO dto,String key)throws ReservationException;
    Reservation viewReservation(Integer rid,String key)throws ReservationException;
    List<Reservation> getAllReservation(String key)throws ReservationException;
    List<Reservation> viewReservationByUserId(Integer uid,String key)throws ReservationException;
    Reservation deleteReservation(Integer rid,String key)throws ReservationException;
    Reservation updateReservation(Integer rid,ReservationDTO dto,String key)throws ReservationException;
}
