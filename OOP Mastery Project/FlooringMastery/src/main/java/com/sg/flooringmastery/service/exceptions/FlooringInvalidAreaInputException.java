/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service.exceptions;

/**
 *
 * @author riddl
 */
public class FlooringInvalidAreaInputException extends Exception {

    public FlooringInvalidAreaInputException() {
    }

    public FlooringInvalidAreaInputException(String message) {
        super(message);
    }

    public FlooringInvalidAreaInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidAreaInputException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidAreaInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    
    
}
