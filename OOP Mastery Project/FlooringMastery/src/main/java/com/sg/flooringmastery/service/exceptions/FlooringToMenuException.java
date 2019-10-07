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
public class FlooringToMenuException extends Exception {

    public FlooringToMenuException() {
    }

    public FlooringToMenuException(String message) {
        super(message);
    }

    public FlooringToMenuException(String message, Throwable cause) {
        super(message, cause);
    }

    public FlooringToMenuException(Throwable cause) {
        super(cause);
    }

    public FlooringToMenuException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
