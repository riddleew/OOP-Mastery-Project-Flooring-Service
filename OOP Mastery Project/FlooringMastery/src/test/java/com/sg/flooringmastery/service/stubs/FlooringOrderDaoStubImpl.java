/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service.stubs;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dao.interfaces.FlooringOrderDao;
import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.model.dto.Product;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public class FlooringOrderDaoStubImpl implements FlooringOrderDao {
    
    Order orderOne;
    Order orderTwo;
    List<Order> orderList = new ArrayList<>();

    public FlooringOrderDaoStubImpl(Order orderOne, Order orderTwo) {
        this.orderOne = orderOne;
        this.orderTwo = orderTwo;
    }

   
    public void loadAllOrders() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void saveAllChanges() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    public Order addAnOrder(Order aOrder, int orderNum) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Order> getAllOrder() {
        List<Order> theStubOrders = new ArrayList<>();
        theStubOrders.add(orderOne);
        theStubOrders.add(orderTwo);
        return theStubOrders;
    }

    
    public Order getAnOrder(int orderNumber) {
        if (orderNumber == orderOne.getOrderNumber()) {
            return orderOne;
        } else if (orderNumber == orderTwo.getOrderNumber()) {
            return orderTwo;
        } else {
            return null;
        }
    }
 
    public void updateAnOrder(int orderNumber, Order changedOrder) {
        orderList.remove(getAnOrder(orderNumber));
        orderList.add(changedOrder);
    }

    
    public Product removeAnItem(String slotId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order addAnOrder(String date, int orderNumber, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order getAnOrder(String date, int orderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Order> getAllOrders(String date) throws FlooringFileForDateDoesNotExistException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateOrder(String date, int orderNumber, Order order) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Order removeOrder(String date, int orderNumber) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Map<String, Map<Integer, Order>> getAllMaps() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
