/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service.stubs;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dao.interfaces.FlooringTaxDao;
import com.sg.flooringmastery.model.dto.Product;
import com.sg.flooringmastery.model.dto.Tax;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author EricR
 */
public class FlooringTaxDaoStubImpl implements FlooringTaxDao {
    
    Tax taxOne;
    Tax taxTwo;
    List<Tax> taxList = new ArrayList<>();

    public FlooringTaxDaoStubImpl(Tax taxOne, Tax taxTwo) {
        this.taxOne = taxOne;
        this.taxTwo = taxTwo;
    }

   
    public void loadAllOrders() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void saveAllChanges() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Tax addTax(Product aTax, String stateAbbreviation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Tax> getAllTaxes() {
        List<Tax> theStubTaxes = new ArrayList<>();
        theStubTaxes.add(taxOne);
        theStubTaxes.add(taxTwo);
        return theStubTaxes;
    }

    
    public Tax getATax(String stateAbbreviation) {
        if (stateAbbreviation.equals(taxOne.getStateAbbreviation())) {
            return taxOne;
        } else if (stateAbbreviation.equals(taxTwo.getStateAbbreviation())) {
            return taxTwo;
        } else {
            return null;
        }
    }
 
    public void updateATax(String stateAbbreviation, Tax changedTax) {
        taxList.remove(getATax(stateAbbreviation));
        taxList.add(changedTax);
    }

    
    public Tax removeAnItem(String slotId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Tax addTax(Tax aTax, String stateAbbreviation) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadAllTaxes() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
