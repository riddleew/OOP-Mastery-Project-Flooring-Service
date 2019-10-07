/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.model.dao;

import com.sg.flooringmastery.model.dao.interfaces.FlooringProductDao;
import com.sg.flooringmastery.model.dto.Product;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author riddl
 */
public class FlooringProductDaoFileImpl implements FlooringProductDao {

    public static final String DELIMITER = ",";
    private final String PRODUCT_FILE;
    private Map<String, Product> products;

    public FlooringProductDaoFileImpl() {
        products = new HashMap<>();
        this.PRODUCT_FILE = "Data/Products.txt";
    }

    public FlooringProductDaoFileImpl(String fileName) {
        products = new HashMap<>();
        this.PRODUCT_FILE = fileName;
    }

    @Override
    public List<Product> getAllProducts() {
        return new ArrayList<>(products.values());
    }

    @Override
    public Product getAProduct(String productType) {
        return products.get(productType);
    }

    @Override
    public Product addProduct(Product aProduct, String productType) {
        Product wasStoredUnderThatProductType = products.put(productType, aProduct);
        return wasStoredUnderThatProductType;
    }

    @Override
    public void loadAllProducts() throws FlooringPersistenceException {
        Scanner scanner;

        try {
            scanner = new Scanner(
                    new BufferedReader(
                            new FileReader(PRODUCT_FILE)));
        } catch (FileNotFoundException e) {
            throw new FlooringPersistenceException(
                    "-_- Could not load product data into memory.", e);
        }
        String currentLine;
        Product currentProduct;
        scanner.nextLine();

        while (scanner.hasNextLine()) {

            currentLine = scanner.nextLine();
            currentProduct = unmarshallProduct(currentLine);
            products.put(currentProduct.getProductType(), currentProduct);
        }
        scanner.close();
    }

    public Product unmarshallProduct(String productAsText) {
        Product productFromFile = new Product();
        String[] productTokens = productAsText.split(DELIMITER);

        productFromFile.setProductType(productTokens[0]);

        // costPerSquareFoot to BigDecimal
        String costPerSquareFootAsString = productTokens[1];
        BigDecimal costPerSquareFoot = new BigDecimal(costPerSquareFootAsString);
        productFromFile.setCostPerSquareFoot(costPerSquareFoot);

        // laborCostPerSquareFoot to BigDecimal
        String laborCostPerSquareFootAsString = productTokens[2];
        BigDecimal laborCostPerSquareFoot = new BigDecimal(laborCostPerSquareFootAsString);
        productFromFile.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        return productFromFile;
    }
}
