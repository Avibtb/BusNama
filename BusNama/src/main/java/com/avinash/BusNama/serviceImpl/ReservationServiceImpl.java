package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.ReservationException;
import com.avinash.BusNama.model.Bus;
import com.avinash.BusNama.model.CurrentUserSession;
import com.avinash.BusNama.model.DTO.ReservationDTO;
import com.avinash.BusNama.model.Reservation;
import com.avinash.BusNama.model.User;
import com.avinash.BusNama.repository.*;
import com.avinash.BusNama.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationServiceImpl implements ReservationService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private CurrentUserSessionRepository currentUserSessionRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private UserRepository userRepository;


    @Override
    public Reservation addReservation(ReservationDTO dto, String key) throws ReservationException {
        CurrentUserSession userSession = currentUserSessionRepository.findByUuid(key);
        if (userSession == null)throw new ReservationException("Invalid session key for user!");
        Integer userId = userSession.getUserID();
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty())throw new ReservationException("User not found with the session key");
        User user = optionalUser.get();
        Bus bus = busRepository.findByRouteFromAndRouteTo(dto.getSource(),dto.getDestination());
        if (bus == null)throw new ReservationException("Bus not found for the given starting to destination");
        Integer seats = bus.getAvailableSeats();
        if(seats < dto.getBookedSeat()) throw new ReservationException("Only "+ seats +" seats are available");
        seats -= dto.getBookedSeat();
        bus.setAvailableSeats(seats);
        Reservation reservation = new Reservation();

        if(dto.getJourneyDate().isBefore(LocalDate.now()))throw new ReservationException("Journey Date should be in future");
        reservation.setSource(dto.getSource());
        reservation.setDestination(dto.getDestination());
        reservation.setDate(dto.getJourneyDate());
        reservation.setStatus("Successful");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setJourneyDate(dto.getJourneyDate());
        reservation.setBus(bus);
        reservation.setFare(bus.getFare() * dto.getBookedSeat());
        reservation.setUser(user);
        return reservationRepository.save(reservation);


    }

    @Override
    public Reservation viewReservation(Integer rid, String key) throws ReservationException {
        return null;
    }

    @Override
    public List<Reservation> getAllReservation(String key) throws ReservationException {
        return null;
    }

    @Override
    public List<Reservation> viewReservationByUserId(Integer uid, String key) throws ReservationException {
        return null;
    }

    @Override
    public Reservation deleteReservation(Integer rid, String key) throws ReservationException {
        return null;
    }

    @Override
    public Reservation updateReservation(Integer rid, ReservationDTO dto, String key) throws ReservationException {
        return null;
    }
}
