/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao.interfaces;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import java.util.List;
import java.util.Map;

/**
 *
 * @author riddl
 */
public interface FlooringOrderDao {
    
    public Order addAnOrder(String date, int orderNumber, Order order);
    
    public Order getAnOrder(String date, int orderNumber);
    
    public List<Order> getAllOrders(String date) throws FlooringFileForDateDoesNotExistException;
    
    public void updateOrder(String date, int orderNumber, Order order);
    
    public Order removeOrder(String date, int orderNumber);
    
    public void loadAllOrders() throws FlooringPersistenceException;
    
    public void saveAllChanges() throws FlooringPersistenceException;
    
    public Map<String, Map<Integer, Order>> getAllMaps();
}
