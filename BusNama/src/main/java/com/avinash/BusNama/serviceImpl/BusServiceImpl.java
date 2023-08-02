package com.avinash.BusNama.serviceImpl;

import com.avinash.BusNama.exception.AdminException;
import com.avinash.BusNama.exception.BusException;
import com.avinash.BusNama.model.Bus;
import com.avinash.BusNama.model.CurrentAdminSession;
import com.avinash.BusNama.model.Route;
import com.avinash.BusNama.repository.BusRepository;
import com.avinash.BusNama.repository.CurrentAdminSessionRepository;
import com.avinash.BusNama.repository.RouteRepository;
import com.avinash.BusNama.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BusServiceImpl implements BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private CurrentAdminSessionRepository currentAdminSessionRepository;


    @Override
    public Bus addBus(Bus bus, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currentAdminSessionRepository.findByaid(key);
        if(admin == null){
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }

        //Finding and checking route
        Route route = new Route(bus.getRouteFrom(),bus.getRouteTo(),bus.getRoute().getDistance());
        if(route == null)
            throw new BusException("Route is not valid");
        //adding route for this new bus
        bus.setRoute(route);

        //adding this new bus to the route
        route.getBusList().add(bus);

        //saving bus
        return busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Bus bus, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currentAdminSessionRepository.findByaid(key);
        if(admin == null){
            throw new AdminException("Key is not valid! Please provide a valid key");
        }
        Optional<Bus> bus1 = busRepository.findById(bus.getBusId());
        if(!bus1.isPresent()){
            throw new BusException("Bus doesn't exist with busId: "+ bus.getBusId());
        }
        Route route = routeRepository.findByRouteFromAndRouteTo(bus.getRouteFrom(),bus.getRouteTo());
        if(route == null){
            Route route1 = new Route(bus.getRouteFrom(),bus.getRouteTo(),bus.getRoute().getDistance());
            routeRepository.save(route1);
            bus.setRoute(route1);
            return busRepository.save(bus);
        }
        routeRepository.save(route);
        bus.setRoute(route);
        return busRepository.save(bus);
    }

    @Override
    public Bus deleteBus(Integer busId, String key) throws BusException, AdminException {
        CurrentAdminSession admin = currentAdminSessionRepository.findByaid(key);
        if(admin ==  null){
            throw new AdminException("Key is not valid! Please provide a valid key.");
        }
        Optional<Bus> bus = busRepository.findById(busId);
        if(bus.isPresent()){
            Bus existingBus = bus.get();
            if(LocalDate.now().isBefore(existingBus.getBusJourneyDate()) && existingBus.getAvailableSeats()!=existingBus.getSeats()){
                throw new BusException("Can't delete scheduled and reserved bus.");
            }
            busRepository.delete(existingBus);
            return existingBus;
        } else throw new BusException("Bus not found with busId: "+ busId);
    }

    //admin + user access methods
    @Override
    public Bus viewBus(Integer busId) throws BusException {
       Optional<Bus> existingBus = busRepository.findById(busId);
       if(!existingBus.isPresent()){
           throw new BusException("No bus exist with this busId: "+ busId);
       }
       return existingBus.get();
    }

    @Override
    public List<Bus> viewBusByBusType(String busType) throws BusException {
        List<Bus> busList = busRepository.findByBusType(busType);
        if(busList.isEmpty()){
            throw new BusException("There are no buses with bus type: "+busType);
        }
        return busList;
    }

    @Override
    public List<Bus> viewAllBuses() throws BusException {
        List<Bus> busList = busRepository.findAll();
        if(busList.isEmpty()){
            throw new BusException("No bus found at this moment. Try again later!");
        }
        return busList;
    }
}
