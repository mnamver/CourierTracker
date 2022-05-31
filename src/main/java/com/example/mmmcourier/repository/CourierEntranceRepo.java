package com.example.mmmcourier.repository;

import com.example.mmmcourier.entity.CourierEntrance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CourierEntranceRepo extends JpaRepository<CourierEntrance, Long> {

    Optional<CourierEntrance> findByCourierId(int courierId);

}
