/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.model.dto.Order;
import com.sg.flooringmastery.model.dto.Product;
import com.sg.flooringmastery.model.dto.Tax;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidAreaInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidCustomerNameInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidDateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringInvalidStateInputException;
import com.sg.flooringmastery.service.exceptions.FlooringNotFutureDateException;
import java.math.BigDecimal;
import org.junit.jupiter.api.Assertions;
import static org.junit.jupiter.api.Assertions.fail;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author EricR
 */
public class FlooringServiceTest {

    FlooringServiceImpl testService;
    Order testOrderOne;
    Order testOrderTwo;
    Tax testTaxOne;
    Tax testTaxTwo;
    Product testProductOne;
    Product testProductTwo;

    public FlooringServiceTest() {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        testService = ctx.getBean("serviceLayer", FlooringServiceImpl.class);
        testOrderOne = ctx.getBean("testOrderOne", Order.class);
    }

    @Test
    public void testCalculations() {
        Order order = testService.calcuate(testOrderOne);
        BigDecimal expectedMaterialCost = new BigDecimal("871.50");
        BigDecimal expectedLaborCost = new BigDecimal("1033.35");
        BigDecimal expectedTax = new BigDecimal("476.21");
        BigDecimal expectedTotal = new BigDecimal("2381.06");

        Assertions.assertEquals(expectedMaterialCost, order.getMaterialCost(), "Both material costs should match");
        Assertions.assertEquals(expectedLaborCost, order.getLaborCost(), "Both labor costs should match");
        Assertions.assertEquals(expectedTax, order.getTax(), "Both taxes should match");
        Assertions.assertEquals(expectedTotal, order.getTotal(), "Both totals should match");
    }

    @Test
    public void testFutureDateValidationUnhappyPath() {
        String date = "06012019";

        try {
            testService.validateIfDateIsFutureDate(date); //if this doesn't throw an exceeption, fail
            fail("Expected FlooringNotFutureDateException");
        } catch (FlooringNotFutureDateException e) {

        }
    }

    @Test
    public void testFutureDateValidationHappyPath() {
        String date = "06012020";

        try {
            testService.validateIfDateIsFutureDate(date); //if this doesn't throw an exceeption, fail
        } catch (FlooringNotFutureDateException e) {
            fail("Expected FlooringNotFutureDateException");
        }
    }

    @Test
    public void testValidateDateUnhappyPath() {
        String date = "6012020";

        try {
            testService.validateDate(date);
            fail("Expected FlooringInvalidDateInputException");
        } catch (FlooringInvalidDateInputException e) {

        }
    }

    @Test
    public void testValidateDateHappyPath() {
        String date = "06012020";

        try {
            testService.validateDate(date);

        } catch (FlooringInvalidDateInputException e) {
            fail("Expected FlooringInvalidDateInputException");
        }
    }

    @Test
    public void testValidateStateInfoUnhappyPath1() {
        String state = "Kent";

        try {
            testService.validateStateInfo(state);
            fail("Expected FlooringInvalidStateInputException");
        } catch (FlooringInvalidStateInputException e) {

        }

    }

    @Test
    public void testValidateStateInfoUnhappyPath2() {
        String state = "K";

        try {
            testService.validateStateInfo(state);
            fail("Expected FlooringInvalidStateInputException");
        } catch (FlooringInvalidStateInputException e) {

        }

    }

    @Test
    public void testValidateStateInfoHappyPath() {
        String state = "KY";

        try {
            testService.validateStateInfo(state);
        } catch (FlooringInvalidStateInputException e) {
            fail("Expected FlooringInvalidStateInputException");
        }
    }

    @Test
    public void validateCustomerName() {
        boolean canNameBeBlank = true;
        String name = "Eric? Riddle";

        try {
            testService.validateCustomerName(name, canNameBeBlank);
            fail("Expected FlooringInvalidCustomerNameInputException");
        } catch (FlooringInvalidCustomerNameInputException e) {

        }
    }

    @Test
    public void validateCustomerName2() {
        boolean canNameBeBlank = false;
        String name = "";

        try {
            testService.validateCustomerName(name, canNameBeBlank);
            fail("Expected FlooringInvalidCustomerNameInputException");
        } catch (FlooringInvalidCustomerNameInputException e) {

        }
    }

    @Test
    public void validateCustomerName3() {
        boolean canNameBeBlank = true;
        String name = "";

        try {
            testService.validateCustomerName(name, canNameBeBlank);

        } catch (FlooringInvalidCustomerNameInputException e) {
            fail("Expected FlooringInvalidCustomerNameInputException");
        }
    }

    @Test
    public void validateCustomerName4() {
        boolean canNameBeBlank = true;
        String name = "Eric Riddle.";

        try {
            testService.validateCustomerName(name, canNameBeBlank);

        } catch (FlooringInvalidCustomerNameInputException e) {
            fail("Expected FlooringInvalidCustomerNameInputException");
        }
    }

    @Test
    public void testValidateArea() {
        BigDecimal area = new BigDecimal("99");
        String areaAsString = area.toString();

        try {
            testService.validateArea(areaAsString);
            fail("Expected FlooringInvalidAreaInputException");
        } catch (FlooringInvalidAreaInputException e) {

        }
    }

    @Test
    public void testValidateArea2() {
        BigDecimal area = new BigDecimal("100");
        String areaAsString = area.toString();

        try {
            testService.validateArea(areaAsString);

        } catch (FlooringInvalidAreaInputException e) {
            fail("Expected FlooringInvalidAreaInputException");
        }
    }
}
