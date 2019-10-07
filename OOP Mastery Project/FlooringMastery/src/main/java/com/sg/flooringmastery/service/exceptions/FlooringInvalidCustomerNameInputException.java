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
public class FlooringInvalidCustomerNameInputException extends Exception {

    public FlooringInvalidCustomerNameInputException() {
    }

    public FlooringInvalidCustomerNameInputException(String message) {
        super(message);
    }

    public FlooringInvalidCustomerNameInputException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringInvalidCustomerNameInputException(Throwable cause) {
        super(cause);
    }

    public FlooringInvalidCustomerNameInputException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
