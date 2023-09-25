package com.avinash.BusNama.controller;


import com.avinash.BusNama.exception.ReservationException;
import com.avinash.BusNama.model.DTO.ReservationDTO;
import com.avinash.BusNama.model.Reservation;
import com.avinash.BusNama.service.ReservationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/busnama")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/user/reservation/add")
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody ReservationDTO dto, @RequestParam(required = false)String key)throws ReservationException{
        Reservation reservation = reservationService.addReservation(dto,key);
        return new ResponseEntity<>(reservation, HttpStatus.CREATED);
    }

    @PutMapping("/user/reservation/update/{rid}")
    public ResponseEntity<Reservation> updateReservation(@Valid @RequestBody ReservationDTO dto,@RequestBody(required = false) String key,@PathVariable Integer rid)throws  ReservationException{
        Reservation reservation = reservationService.updateReservation(rid,dto,key);
        return  new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @DeleteMapping("/user/reservation/delete/{rid}")
    public ResponseEntity<Reservation> deleteReservation(@RequestParam(required = false)String key,@PathVariable Integer rid)throws ReservationException{
        Reservation reservation = reservationService.deleteReservation(rid,key);
        return  new ResponseEntity<>(reservation,HttpStatus.ACCEPTED);
    }

    @GetMapping("reservation/{rid}")
    public ResponseEntity<Reservation> viewReservationById(@PathVariable Integer rid,@RequestBody(required = false)String key)throws ReservationException{
        Reservation reservation = reservationService.viewReservation(rid,key);
        return new ResponseEntity<>(reservation,HttpStatus.OK);
    }

    @GetMapping("reservation/all")
    public ResponseEntity<List<Reservation>> viewAllReservation(@RequestParam(required = false)String key)throws ReservationException{
        List<Reservation> reservations = reservationService.getAllReservation(key);
        return new ResponseEntity<>(reservations,HttpStatus.FOUND);
    }

    @GetMapping("/user/reservation/{uid}")
    public ResponseEntity<List<Reservation>> viewReservationByUserId(@PathVariable Integer uid,@RequestParam(required = false)String key)throws ReservationException{
        List<Reservation> reservations = reservationService.viewReservationByUserId(uid,key);
        return  new ResponseEntity<>(reservations,HttpStatus.FOUND);
    }

}
