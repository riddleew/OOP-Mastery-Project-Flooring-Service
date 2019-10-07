/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service.exceptions;

/**
 *
 * @author EricR
 */
public class FlooringInvalidModeException extends Exception {

    public FlooringInvalidModeException() {
    }

    public FlooringInvalidModeException(String message) {
        super(message);
    }

    public FlooringInvalidModeException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidModeException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidModeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
