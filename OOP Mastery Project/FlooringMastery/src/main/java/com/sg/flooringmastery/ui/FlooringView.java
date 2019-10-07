/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.model.dto.Product;
import java.util.InputMismatchException;
import java.util.List;

/**
 *
 * @author EricR
 */
public class FlooringView {

    private FlooringUserIO io;
    
    public FlooringView(FlooringUserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() throws FlooringInvalidInputException {
        try {
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");
            io.print("* <<Flooring Program>>");
            io.print("*1. Display Orders");
            io.print("*2. Add an Order");
            io.print("*3. Edit an Order");
            io.print("*4. Remove an Order");
            io.print("*5. Save Current Work");
            io.print("*6. Quit");
            io.print("*");
            io.print("* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *");

            return io.readInt("", 1, 6);
        } catch (InputMismatchException e) {
            throw new FlooringInvalidInputException("You must input one of the options.");
        }
    }

    public void displayAllOrders(List<Order> orderList) {
        for (Order currentOrder : orderList) {
            io.print("\nOrder #: " + currentOrder.getOrderNumber());
            io.print("Customer: " + currentOrder.getCustomerName());
            io.print("State: " + currentOrder.getState());
            io.print("Total: $" + currentOrder.getTotal());
        }
    }

    public void displayOrderSummary(String date, Order orderToAdd) {

        io.print("Date: \t\t\t" + date);
        io.print("Customer: \t\t" + orderToAdd.getCustomerName());
        io.print("Order number: \t\t" + orderToAdd.getOrderNumber());
        io.print("State: \t\t\t" + orderToAdd.getState());
        io.print("Tax rate: \t\t%" + orderToAdd.getTaxRate());
        io.print("Product type: \t\t" + orderToAdd.getProductType());
        io.print("Area: \t\t\t" + orderToAdd.getArea() + " sq ft");
        io.print("Cost/sqft: \t\t$" + orderToAdd.getCostPerSquareFoot());
        io.print("Labor cost/sqft: \t$" + orderToAdd.getLaborCostPerSquareFoot());
        io.print("Material cost: \t\t$" + orderToAdd.getMaterialCost());
        io.print("Labor cost: \t\t$" + orderToAdd.getLaborCost());
        io.print("Tax: \t\t\t$" + orderToAdd.getTax());
        io.print("Total: \t\t\t$" + orderToAdd.getTotal());
    }
    
    public int getOrderNumberInfo() throws FlooringInvalidInputException {
        
        int orderNum = 0;
        try {
        orderNum = io.readInt("Please enter an order number: ");
        } catch (InputMismatchException e) {
           throw new FlooringInvalidInputException("You must enter a valid order number.");
        }
        return orderNum;
    }
    

    public String getDateInfo() {
        return io.readString("Please insert date in MMDDYYYY format: ");
    }

    public String getCustomerNameInfo() {
        return io.readString("Please insert a customer name: ");
    }
    
    public String getEditCustomerNameInfo(Order order) {
        return io.readString("Please insert a customer name (" + order.getCustomerName() + "):" );
    }

    public String getStateInfo() {
        return io.readString("Please enter state (e.g. KY): ");
    }
    
    public String getEditStateInfo (Order order) {
        return io.readString("Please enter state (" + order.getState() + "): ");
    }

    public int getProductTypeInfo(List<Product> productList) throws FlooringInvalidInputException {
        try {
            io.print("Please choose a product type from the following options: ");
            io.print("    Type\tCost/sqft\tLabor/sqft");
            int counter = 1;
            for (Product currentProduct : productList) {
                io.print(counter + ".  " + currentProduct.getProductType()
                        + " \t$" + currentProduct.getCostPerSquareFoot()
                        + "\t\t$" + currentProduct.getLaborCostPerSquareFoot());
                counter++;
            }

            return io.readInt("", 1, 4);
        } catch (InputMismatchException e) {
            throw new FlooringInvalidInputException("Bad input.");
        }
    }
    
    public int getEditProductTypeInfo(List<Product> productList, Order order) throws FlooringInvalidInputException {
        try {
            io.print("Please choose a product type (" + order.getProductType() + "):");
            io.print("    Type\tCost/sqft\tLabor/sqft");
            int counter = 1;
            for (Product currentProduct : productList) {
                io.print(counter + ".  " + currentProduct.getProductType()
                        + " \t$" + currentProduct.getCostPerSquareFoot()
                        + "\t\t$" + currentProduct.getLaborCostPerSquareFoot());
                counter++;
            }

            return io.readInt("", 1, 4);
        } catch (InputMismatchException e) {
            throw new FlooringInvalidInputException("Invalid input. Not one of the options.");
        }
    }
    

    public String getAreaInfo() {
        String areaAsString = io.readString("Please enter the area in sq ft "
                + "(must be at least 100sq ft): ");
        return areaAsString;
    }

    public String getEditAreaInfo(Order order) {
        String areaAsString = io.readString("Please enter the area in sq ft (" + order.getArea() + "):");
        return areaAsString;
    }
    
    public void displayAddOrderSummaryIntro() {
        io.print("Thank you. Below is a summary of the order you would like to add.");
    }
    
    public void displayEditOrderSummaryIntro() {
        io.print("Below are the summary of edits you have made.");
    }
    
    public void displayRemoveOrderSummaryIntro() {
        io.print("Below is the order you have requested to remove.");
    }
    
    public void displayRemoveOrderSummaryOutro() {
        io.print("Are you sure you would like to remove this order? (Y/N):");
    }
    
    public void displayTryAgain() {
        io.print("Would you like to try again? (Y/N): ");
    }

    
    public String getYesOrNoResponse() {
        return io.readString("");
    }

    public void displayChangesDetected() {
        io.print("\nChanges detected.");
    }
    
    public String displayNoChangesDetected() {
        return io.readString("No changes detected.\nPress any key to continue.");
    }
    
    public void displaySaveChangesPrompt() {
        io.print("Would you like to save your changes? (Y/N):");
    }
    
    public void displaySaveChangesToFilePrompt() {
        io.print("Would you like to write any changes you've made to file? (Y/N):");
    }
    
    public void displayChangesSavedToFileSuccess() {
        io.print("Any changes were written to file.");
        io.readString("Press enter to continue.");
    }
    
    public void displayChangesSaved() {
        io.print("Changes saved.");
        io.readString("Press enter to continue.");
    }
    
    public void displayChangesNotSaved() {
        io.print("Changes NOT saved.");
        io.readString("Press enter to continue.");
    }
    
    public void displayRemoveOrderSuccess() {
        io.print("Order was successfully removed.");
        io.readString("Press enter to continue.");
    }
    
    public void displayNoRemoveOrder() {
        io.print("Order was not removed.");
        io.readString("Press enter to continue.");
    }
    
    public void displayTrainingModeMessage() {
        io.print("Error: You can not save changes while in Training Mode.");
        io.readString("Press enter to continue.");
    }
    
    public void displayErrorMessageAndClearBuffer(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
        io.readString("");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }
    
    public void displayExitProgramMessage() {
        io.print("Exiting program...");
    }
}
