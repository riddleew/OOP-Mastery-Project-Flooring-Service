/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import java.util.Scanner;

/**
 *
 * @author EricR
 */
public class FlooringUserIOConsoleImpl implements FlooringUserIO {

    Scanner scanner = new Scanner(System.in);

    @Override
    public void print(String message) {
        System.out.println(message);
    }

    @Override
    public double readDouble(String prompt) {
        print(prompt);
        double result = scanner.nextDouble();

        return result;
    }

    @Override
    public double readDouble(String prompt, double min, double max) {
        double result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readDouble(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }

        }

        return result;
    }

    @Override
    public float readFloat(String prompt) {
        print(prompt);
        float result = scanner.nextFloat();

        return result;
    }

    @Override
    public float readFloat(String prompt, float min, float max) {
        float result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readFloat(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;
    }

    @Override
    public int readInt(String prompt) {
        print(prompt);
        int result = scanner.nextInt();
        scanner.nextLine();

        return result;
    }

    @Override
    public int readInt(String prompt, int min, int max) {
        int result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readInt(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }

        }

        return result;
    }

    @Override
    public long readLong(String prompt) {
        print(prompt);
        long result = scanner.nextLong();

        return result;
    }

    @Override
    public long readLong(String prompt, long min, long max) {
        long result = 0;
        boolean badInput = true;

        while (badInput) {
            result = readLong(prompt);
            if (result >= min && result <= max) {
                badInput = false;
            } else {
                print("Input needs to be >= " + min + " and <= " + max);
            }
        }

        return result;
    }

    @Override
    public String readString(String prompt) {
       
        String result = "";
        
        print(prompt);
        result = scanner.nextLine();
        //could return scanner.nextLine();
        
        return result;
    }

}
