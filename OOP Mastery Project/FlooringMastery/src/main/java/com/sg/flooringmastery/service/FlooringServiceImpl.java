/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.service.exceptions.FlooringNotFutureDateException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidYesOrNoResponseException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidStateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidDateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidAreaInputException;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import com.sg.flooringmastery.service.exceptions.FlooringToMenuException;
import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dao.interfaces.FlooringModeDao;
import com.sg.flooringmastery.model.dao.interfaces.FlooringOrderDao;
import com.sg.flooringmastery.model.dao.interfaces.FlooringProductDao;
import com.sg.flooringmastery.model.dao.interfaces.FlooringTaxDao;
import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.model.dto.Product;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidCustomerNameInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidModeException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 *
 * @author riddl
 */
public class FlooringServiceImpl implements FlooringService {

    FlooringOrderDao orderDao;
    FlooringTaxDao taxDao;
    FlooringProductDao productDao;
    FlooringModeDao modeDao;

    public FlooringServiceImpl(FlooringOrderDao orderDao, FlooringTaxDao taxDao, FlooringProductDao productDao, FlooringModeDao modeDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
        this.modeDao = modeDao;
    }

    public FlooringServiceImpl(FlooringOrderDao orderDao, FlooringTaxDao taxDao, FlooringProductDao productDao) {
        this.orderDao = orderDao;
        this.taxDao = taxDao;
        this.productDao = productDao;
    }

    public void load() throws FlooringPersistenceException, FlooringInvalidModeException {
        orderDao.loadAllOrders();
        taxDao.loadAllTaxes();
        productDao.loadAllProducts();
        modeDao.loadMode();
        
        
        if (!modeDao.getMode().equals("Production") && !modeDao.getMode().equals("Training")) {
            throw new FlooringInvalidModeException ("Invalid mode. Not in Training or "
                    + "Production. Please check mode config file.");
        }
    }

    public void save() throws FlooringPersistenceException {
        orderDao.saveAllChanges();
    }

    public void addAnOrder(String date, int orderNumber, Order order) {
        orderDao.addAnOrder(date, orderNumber, order);
    }

    @Override
    public List<Order> getAllOrders(String date) throws FlooringFileForDateDoesNotExistException {
        return orderDao.getAllOrders(date);
    }

    public List<Product> getAllProducts() {
        return productDao.getAllProducts();
    }

    @Override
    public Order getAnOrder(String date, int orderNumber) {
        return orderDao.getAnOrder(date, orderNumber);
    }

    public void editAnOder(String date, int orderNumber, Order updatedOrder) {
        orderDao.updateOrder(date, orderNumber, updatedOrder);
    }

    public void removeAnOrder(String date, int orderNumber) {
        orderDao.removeOrder(date, orderNumber);
    }

    public boolean checkModeForProduction()  {
        boolean isProduction;
        if (modeDao.getMode().equals("Production")) {
            isProduction = true;
        } else {
            isProduction = false;
        } 
        
        return isProduction;
    }

    public Order calcuate(Order orderToAdd) {

        BigDecimal materialCost = new BigDecimal("0.00");
        BigDecimal laborCost = new BigDecimal("0.00");
        BigDecimal tax = new BigDecimal("0.00");
        BigDecimal total = new BigDecimal("0.00");

        materialCost = orderToAdd.getArea().multiply(orderToAdd.getCostPerSquareFoot());
        materialCost = materialCost.setScale(2, RoundingMode.HALF_UP);
        orderToAdd.setMaterialCost(materialCost);

        laborCost = orderToAdd.getArea().multiply(orderToAdd.getLaborCostPerSquareFoot());
        laborCost = laborCost.setScale(2, RoundingMode.HALF_UP);
        orderToAdd.setLaborCost(laborCost);

        BigDecimal taxPt1 = materialCost.add(laborCost);
        BigDecimal taxPt2 = orderToAdd.getTaxRate().divide(new BigDecimal("100"));
        tax = taxPt1.multiply(taxPt2);
        tax = tax.setScale(2, RoundingMode.HALF_UP);
        orderToAdd.setTax(tax);

        total = materialCost.add(laborCost).add(tax);
        orderToAdd.setTotal(total);

        return orderToAdd;
    }

    public Order populateOrder(String customerName, String stateAbbreviation,
            String productType, BigDecimal area)
            throws FlooringFileForDateDoesNotExistException {

        area = area.setScale(2, RoundingMode.HALF_UP);
        Order orderToAdd = new Order(customerName, stateAbbreviation, productType, area);

        int nextOrderNumber = getNextOrderNumber(orderDao.getAllMaps());
        orderToAdd.setOrderNumber(nextOrderNumber);

        BigDecimal taxRate = taxDao.getATax(stateAbbreviation).getTaxRate();
        orderToAdd.setTaxRate(taxRate);

        BigDecimal costPerSquareFoot = productDao.getAProduct(productType).getCostPerSquareFoot();
        orderToAdd.setCostPerSquareFoot(costPerSquareFoot);

        BigDecimal laborCostPerSquareFoot = productDao.getAProduct(productType).getLaborCostPerSquareFoot();
        orderToAdd.setLaborCostPerSquareFoot(laborCostPerSquareFoot);

        return orderToAdd;
    }

    private int getNextOrderNumber(Map mp) throws FlooringFileForDateDoesNotExistException {

        int maxOrderNumber = 0;
        Iterator iterator = mp.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry me = (Map.Entry) iterator.next();
            List<Order> currentList = orderDao.getAllOrders(me.getKey().toString());

            for (Order currentOrder : currentList) {
                if (currentOrder.getOrderNumber() > maxOrderNumber) {
                    maxOrderNumber = currentOrder.getOrderNumber();
                }
            }
        }

        maxOrderNumber += 1;

        return maxOrderNumber;
    }

    public void validateDate(String date) throws FlooringInvalidDateInputException {
        try {
            LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy"));
        } catch (DateTimeParseException e) {
            throw new FlooringInvalidDateInputException("Invalid date input.\n"
                    + "Your input of '" + date + "' does not match the MMDDYYYY format.");
        }
    }

    public void validateIfDateIsFutureDate(String date) throws FlooringNotFutureDateException {
        LocalDate presentDay = LocalDate.now();
        LocalDate ld = LocalDate.parse(date, DateTimeFormatter.ofPattern("MMddyyyy"));
        Period diff = presentDay.until(ld);

        if (diff.getDays() < 1 && diff.getMonths() < 1 && diff.getYears() < 1) {
            throw new FlooringNotFutureDateException("Date must be at least one day in the future.");
        }
    }

    public void validateStateInfo(String stateAbbreviation) throws FlooringInvalidStateInputException {

        if (stateAbbreviation.length() != 2) {
            throw new FlooringInvalidStateInputException("Invalid input. "
                    + "State must be entered in abbreviated format (e.g. KY).");
        }
        if (taxDao.getATax(stateAbbreviation) == null) {
            throw new FlooringInvalidStateInputException("Sorry but we do not currently sell there.");
        }
    }

    public void validateCustomerName(String customerName, boolean canNameBeBlank) 
            throws FlooringInvalidCustomerNameInputException {
            
        String pattern = "^[a-zA-Z0-9. ]*$";
        if (!customerName.matches(pattern)) {
            throw new FlooringInvalidCustomerNameInputException("Invalid customer name. Name can only contain"
                    + " letters, numbers, and periods.");
        } 
        
        if (!canNameBeBlank) {
            if(validateIfCustomerNameIsBlank(customerName)) {
                throw new FlooringInvalidCustomerNameInputException("Invalid customer name. Name cannot be blank.");
            }
        }
        
    }  
    
    public boolean validateIfCustomerNameIsBlank(String customerName) {
        if (customerName.trim().length() == 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkForChanges(Order updatedOrder, Order toUpdate) {

        boolean areChanges;
        if (!updatedOrder.getCustomerName().equals(toUpdate.getCustomerName())
                || !updatedOrder.getState().equalsIgnoreCase(toUpdate.getState())
                || !updatedOrder.getProductType().equals(toUpdate.getProductType())
                || updatedOrder.getArea().compareTo(toUpdate.getArea()) != 0) {
            areChanges = true;
        } else {
            areChanges = false;
        }
        return areChanges;
    }

    public Order copyOrder(Order order) {
        Order orderCopy = new Order();

        orderCopy.setOrderNumber(order.getOrderNumber());
        orderCopy.setCustomerName(order.getCustomerName());
        orderCopy.setState(order.getState());
        orderCopy.setTaxRate(order.getTaxRate());
        orderCopy.setProductType(order.getProductType());
        orderCopy.setArea(order.getArea());
        orderCopy.setCostPerSquareFoot(order.getCostPerSquareFoot());
        orderCopy.setLaborCostPerSquareFoot(order.getLaborCostPerSquareFoot());
        orderCopy.setMaterialCost(order.getMaterialCost());
        orderCopy.setLaborCost(order.getLaborCost());
        orderCopy.setTax(order.getTax());
        orderCopy.setTotal(order.getTotal());

        return orderCopy;
    }

    public void validateYesOrNoResponse(String response) throws
            FlooringInvalidYesOrNoResponseException, FlooringToMenuException {

        if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
            throw new FlooringInvalidYesOrNoResponseException("Input must either be 'Y' or 'N'"
                    + " Please try again.");
        } else if (response.equalsIgnoreCase("n")) {
            throw new FlooringToMenuException();
        }
    }
    
    public void validateYesOrNoResponseOnExit(String response) throws
            FlooringInvalidYesOrNoResponseException, FlooringToMenuException {

        if (!response.equalsIgnoreCase("y") && !response.equalsIgnoreCase("n")) {
            throw new FlooringInvalidYesOrNoResponseException("Input must either be 'Y' or 'N'"
                    + " Please try again.");
        } 
    }

    public void saveOrDiscard(String response, String date, Order orderToAdd, int orderNum) {
        if (response.equalsIgnoreCase("y")) {
            orderDao.addAnOrder(date, orderNum, orderToAdd);
        }
    }

    public void validateArea(String areaAsString) throws FlooringInvalidAreaInputException {
        try {
            new BigDecimal(areaAsString);
        } catch (NumberFormatException e) {
            throw new FlooringInvalidAreaInputException("Invalid input was entered for area.");
        }

        if (new BigDecimal(areaAsString).compareTo(new BigDecimal("100")) < 0) {
            throw new FlooringInvalidAreaInputException("Area needs to be at least 100 sq ft.");
        }
    }

}
