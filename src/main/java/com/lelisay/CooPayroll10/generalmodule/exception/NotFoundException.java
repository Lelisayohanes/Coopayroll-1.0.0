package com.lelisay.CooPayroll10.generalmodule.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class NotFoundException extends RuntimeException {

    private final int statusCode;
    private final String requestInformation;
    private final LocalDateTime timestamp;

    public NotFoundException(int statusCode, String message, String requestInformation) {
        super(message);
        this.statusCode = statusCode;
        this.requestInformation = requestInformation;
        this.timestamp = getCurrentTimestamp();
    }

    // Add getters for statusCode, requestInformation, and timestamp

    private LocalDateTime getCurrentTimestamp() {
        return LocalDateTime.now();
    }
}

