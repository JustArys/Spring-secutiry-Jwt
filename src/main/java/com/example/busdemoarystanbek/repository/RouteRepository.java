package com.example.busdemoarystanbek.repository;

import com.example.busdemoarystanbek.model.Route;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RouteRepository extends JpaRepository<Route, Long> {
    public Route  findByRouteFromAndRouteTo(String routeFrom,String routeTo);
}
