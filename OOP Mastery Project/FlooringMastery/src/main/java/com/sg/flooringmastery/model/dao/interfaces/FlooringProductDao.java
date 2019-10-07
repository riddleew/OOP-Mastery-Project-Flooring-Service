/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao.interfaces;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dto.Product;
import java.util.List;

/**
 *
 * @author riddl
 */
public interface FlooringProductDao {
    
    public Product addProduct(Product aProduct, String productType);
    public Product getAProduct(String productType);
    public List<Product> getAllProducts();
    public void loadAllProducts() throws FlooringPersistenceException;
    
}
