/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service.stubs;

import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dao.interfaces.FlooringProductDao;
import com.sg.flooringmastery.model.dto.Product;
import java.util.ArrayList;
import java.util.List;
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
public class FlooringProductDaoStubImpl implements FlooringProductDao {
    
    Product productOne;
    Product productTwo;
    List<Product> productList = new ArrayList<>();

    public FlooringProductDaoStubImpl(Product productOne, Product productTwo) {
        this.productOne = productOne;
        this.productTwo = productTwo;
    }

   
    public void loadAllOrders() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
    public void saveAllChanges() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Product addProduct(Product aProduct, String productType) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<Product> getAllProducts() {
        List<Product> theStubProducts = new ArrayList<>();
        theStubProducts.add(productOne);
        theStubProducts.add(productTwo);
        return theStubProducts;
    }

    
    public Product getAProduct(String productType) {
        if (productType.equals(productOne.getProductType())) {
            return productOne;
        } else if (productType.equals(productTwo.getProductType())) {
            return productTwo;
        } else {
            return null;
        }
    }
 
    public void updateAProduct(String productType, Product changedProduct) {
        productList.remove(getAProduct(productType));
        productList.add(changedProduct);
    }

    
    public Product removeAnItem(String slotId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadAllProducts() throws FlooringPersistenceException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
