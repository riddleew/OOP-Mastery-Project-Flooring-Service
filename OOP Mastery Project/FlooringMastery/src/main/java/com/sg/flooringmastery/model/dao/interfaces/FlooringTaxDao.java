/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao.interfaces;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dto.Tax;
import java.util.List;

/**
 *
 * @author riddl
 */
public interface FlooringTaxDao {
    
    public Tax addTax(Tax aTax, String stateAbbreviation);
    public List<Tax> getAllTaxes();
    public Tax getATax(String stateAbbreviation);
    public void loadAllTaxes() throws FlooringPersistenceException;
}
