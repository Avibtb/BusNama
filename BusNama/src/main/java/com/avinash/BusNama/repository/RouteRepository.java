package com.avinash.BusNama.repository;

import com.avinash.BusNama.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route,Integer> {
    Route findByRouteFromAndRouteTo(String from, String to);
}
