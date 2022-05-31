package com.example.mmmcourier.exception;

import java.util.UUID;

public class CourierNotFoundExp extends CourierException {
    public CourierNotFoundExp(int courierId) {
        super(courierId + "is not exist!");
    }
}