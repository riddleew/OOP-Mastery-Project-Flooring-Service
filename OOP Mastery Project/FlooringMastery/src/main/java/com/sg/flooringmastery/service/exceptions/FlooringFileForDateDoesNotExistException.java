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
public class FlooringFileForDateDoesNotExistException extends Exception {

    public FlooringFileForDateDoesNotExistException() {
    }

    public FlooringFileForDateDoesNotExistException(String message) {
        super(message);
    }

    public FlooringFileForDateDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringFileForDateDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public FlooringFileForDateDoesNotExistException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
