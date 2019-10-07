/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dao.FlooringProductDaoFileImpl;
import com.sg.flooringmastery.model.dao.FlooringTaxDaoFileImpl;
import com.sg.flooringmastery.model.dto.Tax;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author EricR
 */
public class FlooringTaxDaoFileImplCrudTest {
     
    FlooringTaxDaoFileImpl testDao;
    
    public FlooringTaxDaoFileImplCrudTest() throws IOException {
        String testFileName = "testtaxes.txt";
        new FileWriter(testFileName);
        testDao = new FlooringTaxDaoFileImpl(testFileName);
    }

    @Test
    public void addGetOneTaxTest() {
        
        // ARRANGE
        String stateAbbreviation = "KY";
        Tax toStore =  new Tax(stateAbbreviation, "Kentucky", new BigDecimal("6.00"));
        
        // ACT
        Tax gotBack = testDao.addTax(toStore, stateAbbreviation);
        Tax gottenTax = testDao.getATax(stateAbbreviation);
        
        // ASSERT
        Assertions.assertEquals(gottenTax.getStateAbbreviation(), toStore.getStateAbbreviation(), "State abbreviations should match");
        Assertions.assertEquals(gottenTax.getStateName(), toStore.getStateName(), "State names should match");
        Assertions.assertEquals(gottenTax.getTaxRate(), toStore.getTaxRate(), "Tax rates should match");

        Assertions.assertNull(gotBack, "There should have been a tax in there, yo.");
    }
    
    @Test
    public void addGetAllProductTest() {
        
        // ARRANGE
        String stateAbbreviation = "KY";
        Tax toStore =  new Tax(stateAbbreviation, "Kentucky", new BigDecimal("6.00"));
        
        String stateAbbreviation2 = "WA";
        Tax toStore2 = new Tax(stateAbbreviation, "Washington", new BigDecimal("9.25"));
        
        // ACT
        Tax gotBackFirst = testDao.addTax(toStore, stateAbbreviation);
        Tax gotBackSecond = testDao.addTax(toStore2, stateAbbreviation2);
        
        List<Tax> allDaTaxes = testDao.getAllTaxes();
        
        // ASSERT
        
        Assertions.assertNotNull(allDaTaxes, "our tax list should not be null");
        Assertions.assertEquals(2, allDaTaxes.size(), "there should be 2 taxes in the list.");
        Assertions.assertTrue(allDaTaxes.contains(toStore), "Tax list should have the first Tax stored.");
        Assertions.assertTrue(allDaTaxes.contains(toStore2), "Tax list should have the second tax stored.");
        
        Assertions.assertNull(gotBackFirst,"There shouildn't be a tax returned when storing in an empty dao.");
        Assertions.assertNull(gotBackSecond,"There shouildn't be a tax returned when storing in an empty dao.");
        
    }

    @Test
    public void emptyDaoTest()  {
        // ARRANGE - done in constructor
        
        // ACT - just check that it's empty
        List<Tax> emptyTaxes = testDao.getAllTaxes();
        
        // ASSERT - prove it
        Assertions.assertNotNull(emptyTaxes, "Should be empty list, not null");
        Assertions.assertEquals(0, emptyTaxes.size(), "Should be an empty list.");
    }
    
    @Test
    public void unMarshallTaxTest() {
        // ARRANGE
        String taxLine = "KY,Kentucky,6.00";
        
        // ACT
        Tax fromLine = testDao.unmarshallTax(taxLine);
        
        
        // ASSERT
        Assertions.assertEquals("KY", fromLine.getStateAbbreviation(), "State abbreviation should be KY");
        Assertions.assertEquals("Kentucky", fromLine.getStateName(), "State name should be Kentucky");
        Assertions.assertEquals(new BigDecimal("6.00"), fromLine.getTaxRate(), "Tax rate should be 6.00"); 
    }
}


