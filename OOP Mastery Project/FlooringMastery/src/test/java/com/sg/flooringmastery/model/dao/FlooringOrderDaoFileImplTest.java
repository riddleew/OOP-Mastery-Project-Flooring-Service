/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
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
public class FlooringOrderDaoFileImplTest {
    
    FlooringOrderDaoFileImpl testDao;
    
    public FlooringOrderDaoFileImplTest() throws IOException {
        String testFileName = "testorders.txt";
        new FileWriter(testFileName);
        testDao = new FlooringOrderDaoFileImpl(testFileName);
    }

    @Test
    public void addGetOneOrderTest() {
        // ARRANGE - already know we have a blank DAO because when we make
        // a map it begins empty
        // we also need an item, and an id
        int orderNumber = 1;
        String date = "06012020";
        Order toStore =  new Order(orderNumber, "Ada Lovelace", "CA", new BigDecimal(25.00), "Tile",
                        new BigDecimal("249.00"), new BigDecimal("3.50"), new BigDecimal("4.15"),
                        new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), 
                        new BigDecimal("2381.06"));
        
        // ACT
        Order gotBack = testDao.addAnOrder(date, orderNumber, toStore); 
        Order gottenOrder = testDao.getAnOrder(date, orderNumber);
        
        // ASSERT
        // Check that the item that we tried to store, and the one we got back
        // from the dao after we stored it look identical!!!
        Assertions.assertEquals(gottenOrder.getOrderNumber(), toStore.getOrderNumber(), "Order numbers should match");
        Assertions.assertEquals(gottenOrder.getCustomerName(), toStore.getCustomerName(), "Customer Names should match");
        Assertions.assertEquals(gottenOrder.getState(), toStore.getState(), "States should match");
        Assertions.assertEquals(gottenOrder.getTaxRate(), toStore.getTaxRate(), "Tax Rates should match");
        Assertions.assertEquals(gottenOrder.getProductType(), toStore.getProductType(), "Product types should match");
        Assertions.assertEquals(gottenOrder.getArea(), toStore.getArea(), "Areas should match");
        Assertions.assertEquals(gottenOrder.getCostPerSquareFoot(), toStore.getCostPerSquareFoot(), "Cost/sqft should match");
        Assertions.assertEquals(gottenOrder.getLaborCostPerSquareFoot(), toStore.getLaborCostPerSquareFoot(), "Labor cost/sqft should match");
        Assertions.assertEquals(gottenOrder.getMaterialCost(), toStore.getMaterialCost(), "Material costs should match");
        Assertions.assertEquals(gottenOrder.getLaborCost(), toStore.getLaborCost(), "Labor costs should match");
        Assertions.assertEquals(gottenOrder.getTax(), toStore.getTax(), "Taxes should match");
        Assertions.assertEquals(gottenOrder.getTotal(), toStore.getTotal(), "Totals should match");
       
        
        Assertions.assertNull(gotBack, "There should have been an order in there, yo.");
    }
    
    @Test
    public void addGetAllOrdersTest() throws FlooringFileForDateDoesNotExistException {
        // ARRANGE - already know we have a blank DAO
        // we also need an item, and an id
        int orderNumber = 1;
        String date = "06012020";
        Order toStore =  new Order(orderNumber, "Ada Lovelace", "CA", new BigDecimal(25.00), "Tile",
                        new BigDecimal("249.00"), new BigDecimal("3.50"), new BigDecimal("4.15"),
                        new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), 
                        new BigDecimal("2381.06"));
        
        int orderNumber2 = 2;
        Order toStore2 =  new Order(orderNumber2, "Eric Riddle", "KY", new BigDecimal(6.00), "Laminate",
                        new BigDecimal("127.00"), new BigDecimal("1.75"), new BigDecimal("2.10"),
                        new BigDecimal("222.25"), new BigDecimal("266.70"), new BigDecimal("29.34"), 
                        new BigDecimal("518.29"));
        
        // ACT
        Order gotBackFirst = testDao.addAnOrder(date, orderNumber, toStore);
        Order gotBackSecond = testDao.addAnOrder(date, orderNumber2, toStore2);
        
        List<Order> allDaOrders = testDao.getAllOrders(date);
        
        // ASSERT
        
        Assertions.assertNotNull(allDaOrders, "our order list should not be null");
        Assertions.assertEquals(2, allDaOrders.size(), "there should be 2 orders in the list.");
        Assertions.assertTrue(allDaOrders.contains(toStore), "Order list should have the first order stored.");
        Assertions.assertTrue(allDaOrders.contains(toStore2), "Order list should have the second order stored.");
        
        Assertions.assertNull(gotBackFirst,"There shouildn't be an order returned when storing in an empty dao.");
        Assertions.assertNull(gotBackSecond,"There shouildn't be an order returned when storing in an empty dao.");
        
    }
    
    @Test
    public void addRemoveOrderTest() {
        // ARRANGE
        int orderNumber = 1;
        String date = "06012020";
        Order toStore =  new Order(orderNumber, "Ada Lovelace", "CA", new BigDecimal(25.00), "Tile",
                        new BigDecimal("249.00"), new BigDecimal("3.50"), new BigDecimal("4.15"),
                        new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), 
                        new BigDecimal("2381.06"));
        
        // ACT
        testDao.addAnOrder(date, orderNumber, toStore);
        Order removed = testDao.removeOrder(date, orderNumber);
        Order shouldBeNullBecauseItWasRemoved = testDao.getAnOrder(date, orderNumber);
        
        // ASSERT
        // AS LONG AS YOU HAVE OVERRIDDEN EQUALS YOU CAN SKIP THE PARTS
        // AND GO STRAIGHT TO COMPARING WHOLE OBJECTS
        Assertions.assertEquals(toStore, removed, "Stored order and removed order should match");
        Assertions.assertNull(shouldBeNullBecauseItWasRemoved, "Order should no longer be in DAO");
    }
    
    @Test
    public void addUpdateOrderTest() throws FlooringFileForDateDoesNotExistException {
        String date = "06012020";
        int orderNumber = 1;
        Order firstOrder =  new Order(orderNumber, "Ada Lovelace", "CA", new BigDecimal(25.00), "Tile",
                        new BigDecimal("249.00"), new BigDecimal("3.50"), new BigDecimal("4.15"),
                        new BigDecimal("871.50"), new BigDecimal("1033.35"), new BigDecimal("476.21"), 
                        new BigDecimal("2381.06"));
        Order secondOrder = new Order(orderNumber, "Eric Riddle", "KY", new BigDecimal(6.00), "Laminate",
                        new BigDecimal("127.00"), new BigDecimal("1.75"), new BigDecimal("2.10"),
                        new BigDecimal("222.25"), new BigDecimal("266.70"), new BigDecimal("29.34"), 
                        new BigDecimal("518.29"));
        
        // ACT
        // first add the order
        // then replace the order
        // then get the order, make sure its the second one
        // and that getAll only has one order, the second
        testDao.addAnOrder(date, orderNumber, firstOrder);
        testDao.updateOrder(date, orderNumber, secondOrder);
        
        Order retrieved = testDao.getAnOrder(date, orderNumber);
        List<Order> allDaOrders = testDao.getAllOrders(date);
        
        // ASSERT
        // check that retrieved is equal to second
        // check that list is size 1, and has second
        Assertions.assertEquals(secondOrder, retrieved, "Order should have been replaced by second");
        Assertions.assertEquals(1, allDaOrders.size(), "There should only be one order");
        Assertions.assertTrue(allDaOrders.contains(secondOrder), "Only order left should be second one.");

    }   
}
