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
public class FlooringInvalidDateInputException extends Exception {

    public FlooringInvalidDateInputException() {
    }

    public FlooringInvalidDateInputException(String message) {
        super(message);
    }

    public FlooringInvalidDateInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidDateInputException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidDateInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
