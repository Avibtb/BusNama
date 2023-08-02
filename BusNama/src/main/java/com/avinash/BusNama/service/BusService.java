package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.model.Bus;

import java.util.List;

public interface BusService {
    //admin methods
    Bus addBus(Bus bus,String key) throws BusException, AdminException;
    Bus updateBus(Bus bus,String key)throws BusException,AdminException;
    Bus deleteBus(Integer busId,String key)throws BusException,AdminException;


    //admin + user methods
    Bus viewBus(Integer busId)throws BusException;
    List<Bus> viewBusByBusType(String busType)throws BusException;
    List<Bus> viewAllBuses()throws BusException;



}
