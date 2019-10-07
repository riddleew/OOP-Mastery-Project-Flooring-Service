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
public class FlooringInvalidStateInputException extends Exception {

    public FlooringInvalidStateInputException() {
    }

    public FlooringInvalidStateInputException(String message) {
        super(message);
    }

    public FlooringInvalidStateInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidStateInputException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidStateInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
