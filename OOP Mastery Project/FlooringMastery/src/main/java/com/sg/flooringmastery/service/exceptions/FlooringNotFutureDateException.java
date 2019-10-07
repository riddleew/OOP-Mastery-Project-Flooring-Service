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
public class FlooringNotFutureDateException extends Exception {

    public FlooringNotFutureDateException() {
    }

    public FlooringNotFutureDateException(String message) {
        super(message);
    }

    public FlooringNotFutureDateException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringNotFutureDateException(Throwable cause) {
        super(cause);
    }

    public FlooringNotFutureDateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
