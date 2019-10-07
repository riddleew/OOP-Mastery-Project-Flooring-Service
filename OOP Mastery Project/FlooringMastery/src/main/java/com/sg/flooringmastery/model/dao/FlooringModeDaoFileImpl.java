/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dao.interfaces.FlooringModeDao;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author EricR
 */
public class FlooringModeDaoFileImpl implements FlooringModeDao {

    public static final String DELIMITER = "::";
    private final String MODE_FILE;
    public String mode = "";

    
    public FlooringModeDaoFileImpl() {
        this.MODE_FILE = "Data/Mode.txt";
    }
    
    public FlooringModeDaoFileImpl(String fileName) {
        this.MODE_FILE = fileName;
    }
    
    @Override
    public String getMode() {
        return mode;
    }
    

    @Override
    public void loadMode() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(MODE_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load mode data into memory.", e);
        }
        String currentLine = scanner.nextLine();
        String[] modeTokens = currentLine.split(DELIMITER);
        
        mode = modeTokens[1];
        scanner.close();
    }
    
}
