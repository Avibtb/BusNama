package com.avinash.BusNama.controller;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.model.Bus;
import com.avinash.BusNama.service.BusService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/busnama")
public class BusController {

    @Autowired
    private BusService busService;

    //admin endpoints
    @PostMapping("/admin/bus/add")
    public ResponseEntity<Bus> addBus(@Valid @RequestBody Bus bus, @RequestParam(required = false) String key)throws BusException, AdminException{
        Bus newBus = busService.addBus(bus,key);
        return new ResponseEntity<>(newBus, HttpStatus.CREATED);
    }

    @PutMapping("/admin/bus/update")
    public ResponseEntity<Bus> updateBus(@Valid @RequestBody Bus bus,@RequestParam(required = false) String key)throws BusException,AdminException{
        Bus newBus = busService.updateBus(bus,key);
        return new ResponseEntity<>(newBus,HttpStatus.OK);
    }

    @DeleteMapping("/admin/bus/delete/{busId}")
    public ResponseEntity<Bus> deleteBusByBusId(@PathVariable("busId")Integer busId,@RequestParam(required = false)String key)throws BusException,AdminException{
        Bus deletedBus = busService.deleteBus(busId,key);
        return new ResponseEntity<>(deletedBus,HttpStatus.OK);
    }

    // user and admin both endpoint
    @GetMapping("/bus/all")
    public ResponseEntity<List<Bus>> getAllBuses()throws BusException{
        List<Bus> allBuses = busService.viewAllBuses();
        return new ResponseEntity<>(allBuses,HttpStatus.OK);
    }

    @GetMapping("/bus/{busId}")
    public ResponseEntity<Bus> getBusById(@PathVariable("busId")Integer busId)throws BusException{
        Bus bus = busService.viewBus(busId);
        return new ResponseEntity<>(bus,HttpStatus.OK);
    }

    @GetMapping("/bus/type/{busType}")
    public ResponseEntity<List<Bus>> getBusesByBusType(@PathVariable("busType")String busType)throws BusException{
        List<Bus> busList = busService.viewBusByBusType(busType);
        return new ResponseEntity<>(busList,HttpStatus.OK);
    }
}
