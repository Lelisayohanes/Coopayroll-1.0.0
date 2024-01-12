package com.lelisay.CooPayroll10.generalmodule.exception;

public class RoleAlreadyExistException extends RuntimeException  {
    public RoleAlreadyExistException(String message){
        super(message);
    }
}
