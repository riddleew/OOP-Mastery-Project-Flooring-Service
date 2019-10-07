/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao.interfaces;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;

/**
 *
 * @author EricR
 */
public interface FlooringModeDao {
   
    public String getMode();
    public void loadMode() throws FlooringPersistenceException;
    
}
