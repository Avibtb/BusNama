package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.RouteException;
import com.avinash.BusNama.model.Bus;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.Route;
import com.avinash.BusNama.repository.AdminRepository;
import com.avinash.BusNama.repository.CurrentAdminSessionRepository;
import com.avinash.BusNama.repository.RouteRepository;
import com.avinash.BusNama.service.AdminLoginService;
import com.avinash.BusNama.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteServiceImpl implements RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;

    @Autowired
    private AdminRepository adminRepository;


    @Override
    public Route addRoute(Route route, String key) throws RouteException, AdminException {
        CurrentAdminSession loggedInAdin = currentAdminSessionRepository.findByaid(key);
        if (loggedInAdin == null){
            throw new AdminException("Please provide valid id to add route!");
        }
        Route newRoute = routeRepository.findByRouteFromAndRouteTo(route.getRouteFrom(),route.getRouteTo());
        if(newRoute != null){
            throw new RouteException("Route :"+ newRoute.getRouteFrom() +" to "+newRoute.getRouteTo()+" is already present!");
        }
        List<Bus> busList = new ArrayList<>();
        if(route!=null){
            route.setBusList(busList);
            return routeRepository.save(route);
        }else{
            throw new RouteException("This route is not available");
        }
    }

    @Override
    public List<Route> viewAllRoute() throws RouteException {
       return null;
    }

    @Override
    public Route viewRoute(int routeId) throws RouteException {
        return null;
    }

    @Override
    public Route updateRoute(Route route, String key) throws RouteException, AdminException {
        return null;
    }

    @Override
    public Route deleteRoute(int routeID, String key) throws RouteException, AdminException {
        return null;
    }
}
