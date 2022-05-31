package com.example.mmmcourier.controller;

import com.example.mmmcourier.model.CourierLocation;
import com.example.mmmcourier.service.CourierService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
@RequestMapping("/courier")
public class CourierController {
    private final CourierService courierService;


    /**
     * save courier log and also
     *
     * Store Courier log when any courier enters
     * radius of 100 meters from Migros  stores.
     *
     */
    @PostMapping(value = "/location")
    public ResponseEntity storeCourier(@RequestBody CourierLocation courierLocation) throws IOException {
        courierService.saveCourierLocation(courierLocation);
       return ResponseEntity.ok().build();
    }

    /**
     *
     * get totalTravelDistance for giver courier
     *
     */
    @GetMapping("/total-distance/{courierId}")
    public ResponseEntity<Double> getTotalTravelDistance(@PathVariable int courierId){
        return ResponseEntity.ok(courierService.getTotalTravelDistance(courierId));
    }
}
