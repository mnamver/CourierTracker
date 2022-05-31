package com.example.mmmcourier.service;

import com.example.mmmcourier.Util.Calculater;
import com.example.mmmcourier.entity.Courier;
import com.example.mmmcourier.entity.CourierEntrance;
import com.example.mmmcourier.exception.CourierNotFoundExp;
import com.example.mmmcourier.model.CourierLocation;
import com.example.mmmcourier.model.StoreLocation;
import com.example.mmmcourier.repository.CourierEntranceRepo;
import com.example.mmmcourier.repository.CourierRepo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourierService {

    private final CourierEntranceRepo courierEntranceRepo;
    private final CourierRepo courierRepo;

    @Transactional
    public void saveCourierLocation(CourierLocation courierLocation) throws IOException {


        //update courier log without any condition
        persistCourierLocation(courierLocation);

        List<StoreLocation> storeLocations = getStoreLocations();
        for (StoreLocation storeLocation : storeLocations) {
            double storeLat = storeLocation.getLat();
            double storeLng = storeLocation.getLng();

            if (isEligibleForStore(storeLat, storeLng, courierLocation)) {
                CourierEntrance courierEntrance = CourierEntrance.builder()
                        .courierId(courierLocation.getCourierId())
                        .latitude(courierLocation.getLatitude())
                        .longitude(courierLocation.getLongitude())
                        .time(courierLocation.getTime()).build();

                courierEntranceRepo.save(courierEntrance);
            }
        }
    }

    private void persistCourierLocation(CourierLocation courierLocation) {
        double totalDistance = 0;
        Optional<Courier> courier = courierRepo.findByCourierId(courierLocation.getCourierId());
        if (!courier.isPresent()) {
            Courier courierNew = Courier.builder().courierId(courierLocation.getCourierId())
                    .latitude(courierLocation.getLatitude())
                    .longitude(courierLocation.getLongitude())
                    .totalDistance(totalDistance)
                    .time(courierLocation.getTime()).build();

            courierRepo.save(courierNew);
        } else {
            double distance = Calculater.distFrom(courier.get().getLatitude(), courier.get().getLongitude(), courierLocation.getLatitude(), courierLocation.getLongitude());

            totalDistance = courier.get().getTotalDistance() + distance;
            courier.get().setTotalDistance(totalDistance);
            courierRepo.save(courier.get());
        }


    }

    private boolean isEligibleForStore(double storeLat, double storeLng, CourierLocation courierLocation) {
        return isCourierInsideRadiusOf100Meter(storeLat, storeLng, courierLocation.getLatitude(), courierLocation.getLongitude()) &&
                isOneMinPassed(courierLocation);
    }


    private boolean isCourierInsideRadiusOf100Meter(double storeLat, double storeLng, double courierLat, double courierLng) {
        double distance = Calculater.distFrom(storeLat, storeLng, courierLat, courierLng);
        return distance < 100 ? Boolean.TRUE : Boolean.FALSE;
    }

    private boolean isOneMinPassed(CourierLocation courierLocation) {
        Optional<CourierEntrance> courier = courierEntranceRepo.findByCourierId(courierLocation.getCourierId());
        if(courier.isPresent()){
            return ChronoUnit.SECONDS.between(courier.get().getTime(), courierLocation.getTime())
                    > 60 ? Boolean.TRUE : Boolean.FALSE;
        }
        return Boolean.TRUE;

    }


    private List<StoreLocation> getStoreLocations() throws IOException {
        Reader reader = Files.newBufferedReader(Paths.get("stores.json"));
        List<StoreLocation> storeLocations = new Gson().fromJson(reader, new TypeToken<List<StoreLocation>>() {
        }.getType());
        return storeLocations;
    }


    public Double getTotalTravelDistance(int courierId) {
        Courier courier = courierRepo.findByCourierId(courierId).orElseThrow(() -> new CourierNotFoundExp(courierId));
        return courier.getTotalDistance();
    }
}
