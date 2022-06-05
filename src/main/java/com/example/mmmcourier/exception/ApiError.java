package com.example.mmmcourier.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class ApiError {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    private final Date timestamp;

    @JsonIgnore
    private HttpStatus httpStatus;
    private int status;
    private String resultCode;
    private List<String> messages;

    public ApiError() {
        this.timestamp = Date.from(Instant.now());
    }
}