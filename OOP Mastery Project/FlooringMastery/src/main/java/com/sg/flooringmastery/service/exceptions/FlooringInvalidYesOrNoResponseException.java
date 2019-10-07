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
public class FlooringInvalidYesOrNoResponseException extends Exception {

    public FlooringInvalidYesOrNoResponseException() {
    }

    public FlooringInvalidYesOrNoResponseException(String message) {
        super(message);
    }

    public FlooringInvalidYesOrNoResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidYesOrNoResponseException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidYesOrNoResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
    
    
}
