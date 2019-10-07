    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.service.exceptions.FlooringToMenuException;
import com.sg.flooringmastery.model.dao.FlooringPersistenceException;
import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.service.exceptions.FlooringFileForDateDoesNotExistException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidAreaInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidDateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidStateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidYesOrNoResponseException;
import com.sg.flooringmastery.service.exceptions.FlooringNotFutureDateException;
import com.sg.flooringmastery.service.FlooringServiceImpl;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidCustomerNameInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidModeException;
import com.sg.flooringmastery.ui.FlooringInvalidInputException;
import com.sg.flooringmastery.ui.FlooringView;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 *
 * @author riddl
 */
public class FlooringController {

    FlooringView view;
    FlooringServiceImpl service;
    
    public FlooringController(FlooringServiceImpl service, FlooringView view) {
        this.service = service;
        this.view = view;
    }

    public void run() {
        try {
            service.load();
            boolean keepGoing = true;
            int menuSelection = 0;

            while (keepGoing) {
                try {
                    menuSelection = getMenuSelection();

                    switch (menuSelection) {
                        case 1:
                            displayOrders();
                            break;
                        case 2:
                            addOrder();
                            break;
                        case 3:
                            editOrder();
                            break;
                        case 4:
                            removeOrder();
                            break;
                        case 5:
                            saveCurrentWork();
                            break;
                        case 6:
                            quit();
                            keepGoing = false;
                            break;
                    }
                } catch (FlooringToMenuException e) {
                }
            }
        } catch (FlooringPersistenceException | FlooringInvalidModeException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private int getMenuSelection() {
        try {
            int selection = view.printMenuAndGetSelection();
            return selection;
        } catch (FlooringInvalidInputException e) {
            view.displayErrorMessageAndClearBuffer(e.getMessage());
        }
        return 0;
    }

    private void displayOrders() throws FlooringPersistenceException {

        String date = view.getDateInfo();
        try {
            view.displayAllOrders(service.getAllOrders(date));
        } catch (FlooringFileForDateDoesNotExistException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }

    private void addOrder() throws FlooringToMenuException {
        boolean hasErrors = false;
        boolean canNameBeBlank = false;
        String date = "";
        String customerName = "";
        BigDecimal area = new BigDecimal("0");
        String stateAbbreviation = "";
        String yesOrNo = "";

        // Date info retrieval and validation
        do {
            try {
                date = view.getDateInfo();
                service.validateDate(date);
                service.validateIfDateIsFutureDate(date);
                hasErrors = false;
            } catch (FlooringInvalidDateInputException | FlooringNotFutureDateException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }

        } while (hasErrors);

        // Customer name retrieval and validation
        do {
            try {
                customerName = view.getCustomerNameInfo();
                service.validateCustomerName(customerName, canNameBeBlank);
                hasErrors = false;
            } catch (FlooringInvalidCustomerNameInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        // State abbreviation retrieval and validation
        do {
            try {
                stateAbbreviation = view.getStateInfo().toUpperCase();
                service.validateStateInfo(stateAbbreviation);
                hasErrors = false;
            } catch (FlooringInvalidStateInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        String productType = retrieveAndValidateProductType();

        // Area retrieval and validation
        
        do {
            String areaAsString = view.getAreaInfo();
            try {
                service.validateArea(areaAsString);
                area = new BigDecimal(areaAsString);
                hasErrors = false;
            } catch (FlooringInvalidAreaInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        Order orderToAdd = new Order();
        try {
            orderToAdd = service.populateOrder(customerName, stateAbbreviation, productType, area);
        } catch (FlooringFileForDateDoesNotExistException e) {
        }
        orderToAdd = service.calcuate(orderToAdd);
        view.displayAddOrderSummaryIntro();
        view.displayOrderSummary(date, orderToAdd);
        view.displaySaveChangesPrompt();

        yesOrNo = view.getYesOrNoResponse();

        do {
            try {
                service.validateYesOrNoResponse(yesOrNo);
                hasErrors = false;
            } catch (FlooringInvalidYesOrNoResponseException e) {
                view.displayErrorMessage(e.getMessage());

                yesOrNo = view.getYesOrNoResponse();
                hasErrors = true;
            }
        } while (hasErrors);

        service.saveOrDiscard(yesOrNo, date, orderToAdd, orderToAdd.getOrderNumber());
        if (yesOrNo.equalsIgnoreCase("y")) {
            view.displayChangesSaved();
        } else {
            view.displayChangesNotSaved();
        }

    }

    private void editOrder() throws FlooringToMenuException {

        boolean hasErrors;
        boolean areChanges;
        String date = "";
        int orderNumber = 0;
        String name = "";
        boolean canNameBeBlank = true;
        String stateAbbreviation = "";
        String productType = "";
        int productTypeAsInt = 0;
        BigDecimal area = new BigDecimal("0.00");
        String yesOrNo = "";
        
        // GET DATE AND ORDER NUMBER
        do {
            try {
                date = view.getDateInfo();
                service.validateDate(date);
                service.getAllOrders(date);
                orderNumber = view.getOrderNumberInfo();
                hasErrors = false;
            } catch (FlooringInvalidInputException | FlooringInvalidDateInputException
                    | FlooringFileForDateDoesNotExistException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        // SET UP ORDER TO EDIT
        Order toUpdate = service.getAnOrder(date, orderNumber);
        Order updatedOrder = service.copyOrder(toUpdate);

        // EDIT CUSTOMER NAME
        do {
            try {
                name = view.getEditCustomerNameInfo(toUpdate);
                service.validateCustomerName(name, canNameBeBlank);
                hasErrors = false;
            } catch (FlooringInvalidCustomerNameInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        if (service.validateIfCustomerNameIsBlank(name)) {
            updatedOrder.setCustomerName(toUpdate.getCustomerName());
        } else {
            updatedOrder.setCustomerName(name);
        }

        // EDIT STATE ABBREVIATION
        do {
            try {
                stateAbbreviation = view.getEditStateInfo(toUpdate).toUpperCase();
                service.validateStateInfo(stateAbbreviation);
                hasErrors = false;
            } catch (FlooringInvalidStateInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        updatedOrder.setState(stateAbbreviation);
        
        // EDIT PRODUCT TYPE
        do {
            try {
                productTypeAsInt = view.getEditProductTypeInfo(service.getAllProducts(), toUpdate);
                hasErrors = false;
            } catch (FlooringInvalidInputException e) {
                view.displayErrorMessageAndClearBuffer(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        productType = assignProductType(productTypeAsInt, productType);
        updatedOrder.setProductType(productType);

        do {
            String areaAsString = view.getEditAreaInfo(toUpdate);
            try {
                service.validateArea(areaAsString);
                area = new BigDecimal(areaAsString);
                hasErrors = false;
            } catch (FlooringInvalidAreaInputException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        area = area.setScale(2, RoundingMode.HALF_UP);
        updatedOrder.setArea(area);

        areChanges = service.checkForChanges(updatedOrder, toUpdate);

        if (areChanges) {
            view.displayChangesDetected();
            updatedOrder = service.calcuate(updatedOrder);
            view.displayEditOrderSummaryIntro();
            view.displayOrderSummary(date, updatedOrder);
            view.displaySaveChangesPrompt();
            yesOrNo = view.getYesOrNoResponse();
            do {
                try {
                    service.validateYesOrNoResponse(yesOrNo);
                    hasErrors = false;
                    if (yesOrNo.equalsIgnoreCase("y")) {
                        service.editAnOder(date, orderNumber, updatedOrder);
                        view.displayChangesSaved();
                    } else {
                        view.displayChangesNotSaved();
                    }
                } catch (FlooringInvalidYesOrNoResponseException e) {
                    view.displayErrorMessage(e.getMessage());
                    yesOrNo = view.getYesOrNoResponse();
                    hasErrors = true;
                }
            } while (hasErrors);
        } else {
            view.displayNoChangesDetected();
        }
    }

    public void removeOrder() throws FlooringToMenuException {
        boolean hasErrors = false;
        String date = "";
        int orderNumber = 0;
        String yesOrNo = "";

        do {
            try {
                date = view.getDateInfo();
                service.validateDate(date);
                service.getAllOrders(date);
                orderNumber = view.getOrderNumberInfo();
                hasErrors = false;
            } catch (FlooringInvalidInputException | FlooringInvalidDateInputException
                    | FlooringFileForDateDoesNotExistException e) {
                view.displayErrorMessage(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        Order orderToRemove = service.getAnOrder(date, orderNumber);
        view.displayRemoveOrderSummaryIntro();
        view.displayOrderSummary(date, orderToRemove);
        view.displayRemoveOrderSummaryOutro();

        yesOrNo = view.getYesOrNoResponse();
        do {
            try {
                service.validateYesOrNoResponse(yesOrNo);
                hasErrors = false;
            } catch (FlooringInvalidYesOrNoResponseException e) {
                view.displayErrorMessageAndClearBuffer(e.getMessage());

                yesOrNo = view.getYesOrNoResponse();
                hasErrors = true;
            }
        } while (hasErrors);

        if (yesOrNo.equalsIgnoreCase("y")) {
            service.removeAnOrder(date, orderNumber);
            view.displayRemoveOrderSuccess();
        } else {
            view.displayNoRemoveOrder();
        }
    }

    public void saveCurrentWork() throws FlooringToMenuException, FlooringPersistenceException {
        boolean hasErrors = false;
        String yesOrNo = "";

        if (service.checkModeForProduction()) {
            view.displaySaveChangesToFilePrompt();
            yesOrNo = view.getYesOrNoResponse();
            do {
                try {
                    service.validateYesOrNoResponse(yesOrNo);
                    hasErrors = false;
                } catch (FlooringInvalidYesOrNoResponseException e) {
                    view.displayErrorMessage(e.getMessage());

                    yesOrNo = view.getYesOrNoResponse();
                    hasErrors = true;
                }
            } while (hasErrors);
            if (yesOrNo.equalsIgnoreCase("y")) {
                service.save();
                view.displayChangesSavedToFileSuccess();
            } else {
                view.displayChangesNotSaved();
            }
        } else {
            view.displayTrainingModeMessage();
        }

    }

    public void quit() throws FlooringToMenuException, FlooringPersistenceException {

        boolean hasErrors = false;
        String yesOrNo = "";

        if (service.checkModeForProduction()) {
            view.displaySaveChangesToFilePrompt();
            yesOrNo = view.getYesOrNoResponse();
            do {
                try {
                    service.validateYesOrNoResponseOnExit(yesOrNo);
                    hasErrors = false;
                } catch (FlooringInvalidYesOrNoResponseException e) {
                    view.displayErrorMessage(e.getMessage());

                    yesOrNo = view.getYesOrNoResponse();
                    hasErrors = true;
                }
            } while (hasErrors);
            if (yesOrNo.equalsIgnoreCase("y")) {
                service.save();
                view.displayChangesSavedToFileSuccess();
                view.displayExitProgramMessage();
            } else {
                view.displayChangesNotSaved();
                view.displayExitProgramMessage();
            }
        } else {
            view.displayExitProgramMessage();
        }

    }

    private String retrieveAndValidateProductType() throws FlooringToMenuException {
        boolean hasErrors = false;
        int productTypeInt = 0;
        String productType = "";

        do {
            try {
                productTypeInt = view.getProductTypeInfo(service.getAllProducts());
                hasErrors = false;
            } catch (FlooringInvalidInputException e) {
                view.displayErrorMessageAndClearBuffer(e.getMessage());
                tryAgain();
                hasErrors = true;
            }
        } while (hasErrors);

        switch (productTypeInt) {
            case 1:
                productType = "Laminate";
                break;
            case 2:
                productType = "Wood";
                break;
            case 3:
                productType = "Tile";
                break;
            case 4:
                productType = "Carpet";
                break;
        }

        return productType;

    }

    private String assignProductType(int productTypeInt, String productType) {

        switch (productTypeInt) {
            case 1:
                productType = "Laminate";
                break;
            case 2:
                productType = "Wood";
                break;
            case 3:
                productType = "Tile";
                break;
            case 4:
                productType = "Carpet";
                break;
        }
        return productType;
    }

    public void tryAgain() throws FlooringToMenuException {

        boolean hasErrors = false;
        view.displayTryAgain();
        String response = view.getYesOrNoResponse();

        do {
            try {
                service.validateYesOrNoResponse(response);
                hasErrors = false;
            } catch (FlooringInvalidYesOrNoResponseException e) {
                view.displayErrorMessage(e.getMessage());
                response = view.getYesOrNoResponse();
                hasErrors = true;
            }
        } while (hasErrors);
    }

}
