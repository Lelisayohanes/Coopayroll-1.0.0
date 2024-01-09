package com.lelisay.CooPayroll10.coremodule.user.exception;

public class UserAlreadyExistsException extends RuntimeException {
    //customize and add status data error
    public UserAlreadyExistsException(String message) {
        super(message);
    }


}
