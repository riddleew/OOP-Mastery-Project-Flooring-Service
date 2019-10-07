/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;


import com.sg.flooringmastery.model.dao.FlooringProductDaoFileImpl;
import com.sg.flooringmastery.model.dto.Product;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *
 * @author EricR
 */
public class FlooringProductDaoFileImplCrudTest {
    
    FlooringProductDaoFileImpl testDao;
    
    public FlooringProductDaoFileImplCrudTest() throws IOException {
        String testFileName = "testproducts.txt";
        new FileWriter(testFileName);
        testDao = new FlooringProductDaoFileImpl(testFileName);
    }

    @Test
    public void addGetOneProductTest() {
        
        // ARRANGE
        String productType = "Wood";
        Product toStore =  new Product(productType, new BigDecimal("5.15"), new BigDecimal("4.75"));
        
        // ACT
        Product gotBack = testDao.addProduct(toStore, productType);
        Product gottenProduct = testDao.getAProduct(productType);
        
        // ASSERT
        Assertions.assertEquals(gottenProduct.getProductType(), toStore.getProductType(), "Product types should match");
        Assertions.assertEquals(gottenProduct.getCostPerSquareFoot(), toStore.getCostPerSquareFoot(), "Cost/sqft should match");
        Assertions.assertEquals(gottenProduct.getLaborCostPerSquareFoot(), toStore.getLaborCostPerSquareFoot(), "Labor cost/sqft should match");

        Assertions.assertNull(gotBack, "There should have been a product in there, yo.");
    }
    
    @Test
    public void addGetAllProductTest() {
        
        // ARRANGE
        String productType = "Carpet";
        Product toStore =  new Product(productType, new BigDecimal("2.25"), new BigDecimal("2.10"));
        
        String productType2 = "Laminate";
        Product toStore2 = new Product(productType2, new BigDecimal("1.75"), new BigDecimal("2.10"));
        
        // ACT
        Product gotBackFirst = testDao.addProduct(toStore, productType);
        Product gotBackSecond = testDao.addProduct(toStore2, productType2);
        
        List<Product> allDaProducts = testDao.getAllProducts();
        
        // ASSERT
        
        Assertions.assertNotNull(allDaProducts, "our product list should not be null");
        Assertions.assertEquals(2, allDaProducts.size(), "there should be 2 products in the list.");
        Assertions.assertTrue(allDaProducts.contains(toStore), "Product list should have the first product stored.");
        Assertions.assertTrue(allDaProducts.contains(toStore2), "Product list should have the second product stored.");
        
        Assertions.assertNull(gotBackFirst,"There shouildn't be a product returned when storing in an empty dao.");
        Assertions.assertNull(gotBackSecond,"There shouildn't be a product returned when storing in an empty dao.");
        
    }

    @Test
    public void emptyDaoTest()  {
        // ARRANGE - done in constructor
        
        // ACT - just check that it's empty
        List<Product> emptyProducts = testDao.getAllProducts();
        
        // ASSERT - prove it
        Assertions.assertNotNull(emptyProducts, "Should be empty list, not null");
        Assertions.assertEquals(0, emptyProducts.size(), "Should be an empty list.");
    }
    
    @Test
    public void unMarshallProductTest() {
        // ARRANGE
        String productLine = "Wood,5.15,4.75";
        
        // ACT
        Product fromLine = testDao.unmarshallProduct(productLine);
        
        
        // ASSERT
        Assertions.assertEquals("Wood", fromLine.getProductType(), "Product type should be Wood");
        Assertions.assertEquals("5.15", fromLine.getCostPerSquareFoot().toString(), "Cost/sqft should be 5.15");
        Assertions.assertEquals(new BigDecimal("4.75"), fromLine.getLaborCostPerSquareFoot(), "Labor cost/sqft should be 4.75"); 
    }
}
