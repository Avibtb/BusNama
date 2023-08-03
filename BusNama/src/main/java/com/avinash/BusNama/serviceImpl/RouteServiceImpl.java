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
import java.util.Optional;

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
      List<Route> routes = routeRepository.findAll();
      if(routes.isEmpty()){
          throw new RouteException("No route available!");
      }else
          return routes;
    }

    @Override
    public Route viewRoute(int routeId) throws RouteException {
        Optional<Route> route = routeRepository.findById(routeId);
        return route.orElseThrow( () -> new RouteException("There is no route present of this routId: "+ routeId));
    }

    @Override
    public Route updateRoute(Route route, String key) throws RouteException, AdminException {
        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByaid(key);
        if (loggedInAdmin == null){
            throw new AdminException("Please provide a valid id to add route!");
        }
        Optional<Route> existedRoute = routeRepository.findById(route.getRouteID());
        if(existedRoute.isPresent()){
            Route presentRoute = existedRoute.get();
            List<Bus> busList = presentRoute.getBusList();
            if(!busList.isEmpty()) throw new RouteException("Cannot update running route! Buses are already scheduled in the route.");
            return  routeRepository.save(route);
        }else
            throw new RouteException("Route doesn't exist of this routeId: "+ route.getRouteID());
    }

    @Override
    public Route deleteRoute(int routeID, String key) throws RouteException, AdminException {
        CurrentAdminSession loggedInAdmin = currentAdminSessionRepository.findByaid(key);
        if(loggedInAdmin == null){
            throw new AdminException("Please provide a valid id to add route !");
        }
        Optional<Route> route = routeRepository.findById(routeID);
        if(route.isPresent()){
            Route existingRoute = route.get();
            routeRepository.delete(existingRoute);
            return existingRoute;
        }else
            throw new RouteException("There is no route of this routeId: "+routeID);

    }
}
