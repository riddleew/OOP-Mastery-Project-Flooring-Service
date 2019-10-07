/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dao.interfaces.FlooringTaxDao;
import com.sg.flooringmastery.model.dto.Tax;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author riddl
 */
public class FlooringTaxDaoFileImpl implements FlooringTaxDao {
    
    public static final String DELIMITER = ",";
    private final String TAX_FILE;
    private Map<String, Tax> taxes;
    
    public FlooringTaxDaoFileImpl() {
        taxes = new HashMap<>();
        this.TAX_FILE = "Data/Taxes.txt";
    }
    
    public FlooringTaxDaoFileImpl(String fileName) {
        taxes = new HashMap<>();
        this.TAX_FILE = fileName;
    }
    
    @Override
    public Tax addTax(Tax aTax, String stateAbbreviation) {
        Tax wasStoredUnderThatStateAbbreviation = taxes.put(stateAbbreviation, aTax);
        return wasStoredUnderThatStateAbbreviation;
    }
    
    @Override
    public List<Tax> getAllTaxes() {
        return new ArrayList<>(taxes.values());
    }
    
    @Override
    public Tax getATax(String stateAbbreviation) {
        return taxes.get(stateAbbreviation);
    } 
    
    @Override
    public void loadAllTaxes() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(TAX_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load tax data into memory.", e);
        }
        String currentLine;
        Tax currentTax;
        scanner.nextLine();

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentTax = unmarshallTax(currentLine);
            taxes.put(currentTax.getStateAbbreviation(), currentTax);
        }
        scanner.close();
    }
    
    public Tax unmarshallTax(String taxAsText) {
        Tax taxFromFile = new Tax();
        String[] taxTokens = taxAsText.split(DELIMITER);
        
        taxFromFile.setStateAbbreviation(taxTokens[0]);
        taxFromFile.setStateName(taxTokens[1]);
        
        // taxRate to BigDecimal
        String taxRateAsString = taxTokens[2];
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        taxFromFile.setTaxRate(taxRate);

        return taxFromFile;
    }
    
}
