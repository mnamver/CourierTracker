package com.example.mmmcourier.repository;

import com.example.mmmcourier.entity.Courier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface CourierRepo extends JpaRepository<Courier, Long> {

    Optional<Courier> findByCourierId(int courierId);
}
