/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dao.interfaces.FlooringOrderDao;
import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author riddl
 */
public class FlooringOrderDaoFileImpl implements FlooringOrderDao {

    public static final String DELIMITER = ",";

    private Map<String, Map<Integer, Order>> maps;
    private String orderFile;
    private List<String> fileNameList = new ArrayList<>();
    final File folder = new File("Orders");

    public FlooringOrderDaoFileImpl() {
        maps = new HashMap<>();
    }

    public FlooringOrderDaoFileImpl(String fileName) {
        maps = new HashMap<>();
        this.orderFile = fileName;
    }

    @Override
    public Order addAnOrder(String date, int orderNumber, Order order) {

        Map<Integer, Order> newOrders = new HashMap<>();
        Order wasStoredUnderThatOrderNumber = new Order();
        if (maps.get(date) != null) {
            wasStoredUnderThatOrderNumber = maps.get(date).put(orderNumber, order);
        } else {
            wasStoredUnderThatOrderNumber = newOrders.put(orderNumber, order);
            maps.put(date, newOrders);
        }

        return wasStoredUnderThatOrderNumber;
    }

    @Override
    public Order getAnOrder(String date, int orderNumber) {
        return maps.get(date).get(orderNumber);
    }

    @Override
    public List<Order> getAllOrders(String date) throws FlooringFileForDateDoesNotExistException {

        Map<Integer, Order> tempMapForAllOrdersDisplay = new HashMap<>();
        tempMapForAllOrdersDisplay = maps.get(date);
        if (tempMapForAllOrdersDisplay == null) {
            throw new FlooringFileForDateDoesNotExistException("Sorry. We don't have an order for that date.");
        }
        return new ArrayList<Order>(tempMapForAllOrdersDisplay.values());
    }

    @Override
    public void updateOrder(String date, int orderNumber, Order order) {
        maps.get(date).replace(orderNumber, order);
    }

    @Override
    public Order removeOrder(String date, int orderNumber) {
        Order removed = maps.get(date).remove(orderNumber);
        return removed;
    }

    @Override
    public Map<String, Map<Integer, Order>> getAllMaps() {
        return maps;
    }

    @Override
    public void loadAllOrders() throws FlooringPersistenceException {
        listFilesForFolder(folder);
        Scanner scanner;

        for (String orderFile : fileNameList) {
            try {
                scanner = new Scanner(
                        new BufferedReader(
                                new FileReader("Orders/" + orderFile)));
            } catch (FileNotFoundException e) {
                throw new FlooringPersistenceException(
                        "File does not exist", e);
            }
            String currentLine;
            Order currentOrder;
            scanner.nextLine();

            Map<Integer, Order> orders = new HashMap<>();
            while (scanner.hasNextLine()) {

                currentLine = scanner.nextLine();
                currentOrder = unmarshallOrder(currentLine);
                orders.put(currentOrder.getOrderNumber(), currentOrder);
            }
            scanner.close();

            String date = orderFile.substring(7, 15);
            maps.put(date, orders);
        }
    }

    public void listFilesForFolder(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFilesForFolder(fileEntry);
            } else {
                fileNameList.add(fileEntry.getName());
            }
        }
    }
    @Override
    public void saveAllChanges() throws FlooringPersistenceException {
        PrintWriter out;

        try {
            for (String date : maps.keySet()) {
                if (maps.get(date).size() != 0) {
                    out = new PrintWriter(new FileWriter("Orders/Orders_" + date + ".txt"));
                    
                    out.println("OrderNumber,CustomerName,State,TaxRate,ProductType,Area,"
                            + "CostPerSquareFoot,LaborCostPerSquareFoot,MaterialCost,"
                            + "LaborCost,Tax,Total");
                    String orderAsText;
                    Map<Integer, Order> tempMap = maps.get(date);

                    for (int orderNum : tempMap.keySet()) {
                        Order order = tempMap.get(orderNum);
                        orderAsText = marshallOrder(order);
                        out.println(orderAsText);
                        out.flush();
                    }
                    out.close();
                } else { 
                    // delete file
                    Path fileToDeletePath = Paths.get("Orders/Orders_" + date + ".txt");
                    Files.delete(fileToDeletePath);
                }
            }
        } catch (IOException e) {
            throw new FlooringPersistenceException("Could not save order data.");
        }

    }

    public Order unmarshallOrder(String orderAsText) {
        Order orderFromFile = new Order();
        String[] orderTokens = orderAsText.split(DELIMITER);

        // Order Number to int
        String orderNumberAsString = orderTokens[0];
        int orderNumber = Integer.parseInt(orderNumberAsString);
        orderFromFile.setOrderNumber(orderNumber);

        orderFromFile.setCustomerName(orderTokens[1]);
        orderFromFile.setState(orderTokens[2]);

        // taxRate to BigDecimal
        String taxRateAsString = orderTokens[3];
        BigDecimal taxRate = new BigDecimal(taxRateAsString);
        orderFromFile.setTaxRate(taxRate);

        orderFromFile.setProductType(orderTokens[4]);

        // area to BigDecimal
        String areaAsString = orderTokens[5];
        BigDecimal area = new BigDecimal(areaAsString);
        orderFromFile.setArea(area);

        // costPerSquareFoot to BigDecimal
        String costPerSquareFootAsString = orderTokens[6];
        BigDecimal costPerSquareFoot = new BigDecimal(costPerSquareFootAsString);
        orderFromFile.setCostPerSquareFoot(costPerSquareFoot);

        // laborCostPerSquareFoot to BigDecimal
        String laborCostPerSquareFootAsString = orderTokens[7];
        BigDecimal laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFootAsString);
        orderFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        // materialCost to BigDecimal
        String materialCostAsString = orderTokens[8];
        BigDecimal materialCost = new BigDecimal(materialCostAsString);
        orderFromFile.setMaterialCost(materialCost);

        // laborCost to BigDecimal
        String laborCostAsString = orderTokens[9];
        BigDecimal laborCost = new BigDecimal(laborCostAsString);
        orderFromFile.setLaborCost(laborCost);

        // tax to BigDecimal
        String taxAsString = orderTokens[10];
        BigDecimal tax = new BigDecimal(taxAsString);
        orderFromFile.setTax(tax);

        // total to BigDecimal
        String totalAsString = orderTokens[11];
        BigDecimal total = new BigDecimal(totalAsString);
        orderFromFile.setTotal(total);

        return orderFromFile;
    }

    public String marshallOrder(Order anOrder) {
        String orderAsText = "";

        orderAsText = anOrder.getOrderNumber() + DELIMITER;
        orderAsText += anOrder.getCustomerName() + DELIMITER;
        orderAsText += anOrder.getState() + DELIMITER;
        orderAsText += anOrder.getTaxRate() + DELIMITER;
        orderAsText += anOrder.getProductType() + DELIMITER;
        orderAsText += anOrder.getArea() + DELIMITER;
        orderAsText += anOrder.getCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getLaborCostPerSquareFoot() + DELIMITER;
        orderAsText += anOrder.getMaterialCost() + DELIMITER;
        orderAsText += anOrder.getLaborCost() + DELIMITER;
        orderAsText += anOrder.getTax() + DELIMITER;
        orderAsText += anOrder.getTotal();

        return orderAsText;
    }
}
