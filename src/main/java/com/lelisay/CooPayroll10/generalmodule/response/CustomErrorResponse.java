package com.lelisay.CooPayroll10.generalmodule.response;

// CustomErrorResponse.java

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CustomErrorResponse extends Throwable {
    @JsonProperty("statusCode")
    private int statusCode;

    @JsonProperty("errorMessage")
    private String errorMessage;

    @JsonProperty("requestInformation")
    private String requestInformation;

    @JsonProperty("TIMESTAMP")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    // Constructors, getters, and setters

    public CustomErrorResponse(int statusCode, String errorMessage, String requestInformation) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
        this.requestInformation = requestInformation;
        this.timestamp = LocalDateTime.now();
    }
}
