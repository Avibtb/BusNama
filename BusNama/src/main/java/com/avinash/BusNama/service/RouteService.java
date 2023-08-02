package com.avinash.BusNama.service;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.RouteException;
import com.avinash.BusNama.model.Route;

import java.util.List;

public interface RouteService {
    Route addRoute(Route route,String key) throws RouteException, AdminException;
    List<Route> viewAllRoute()throws RouteException;
    Route viewRoute(int routeId) throws RouteException;
    Route updateRoute(Route route,String key)throws RouteException,AdminException;
    Route deleteRoute(int routeID,String key)throws RouteException,AdminException;
}
