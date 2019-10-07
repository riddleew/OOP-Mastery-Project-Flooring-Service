/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dto.Order;
import java.util.List;

/**
 *
 * @author riddl
 */
public interface FlooringService {
    
    //public void addAnOrder(String orderDate,  )
    
    public List<Order> getAllOrders(String date) throws FlooringFileForDateDoesNotExistException;
    
    public Order getAnOrder(String date, int orderNumber);
  
}
