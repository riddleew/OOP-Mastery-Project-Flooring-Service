/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

/**
 *
 * @author riddl
 */
public class FlooringPersistenceException extends Exception {

    public FlooringPersistenceException() {
    }

    public FlooringPersistenceException(String message) {
        super(message);
    }

    public FlooringPersistenceException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringPersistenceException(Throwable cause) {
        super(cause);
    }

    public FlooringPersistenceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    
}
