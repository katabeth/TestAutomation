package com.sparta.kch;

import com.sparta.kch.Exceptions.DivideByZeroException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CalculatorStepDefs {

    private Calculator calculator;
    private Integer actual;
    private DivideByZeroException exception;
    @Given("I have a calculator")
    public void iHaveACalculator() {
        calculator = new Calculator();
    }
    @And("I enter {int} and {int} into the calculator")
    public void iEnterAndIntoTheCalculator(int arg0, int arg1) {
        calculator.setNum1(arg0);
        calculator.setNum2(arg1);
    }
//    @And("I enter the numbers below to a list")
//    public void iEnterTheNumbersBelowToAList() {
//        calculator.setNumbers();
//    }
    @When("I press add")
    public void iPressAdd() {
        actual = calculator.add();
    }
    @When("I press subtract")
    public void iPressSubtract() {
        actual = calculator.subtract();
    }
    @When("I press multiply")
    public void iPressMultiply() {
        actual = calculator.multiply();
    }
    @When("I press divide")
    public void iPressDivide() {
        try {
            actual = calculator.divide();
        } catch (DivideByZeroException e) {
            exception = e;
        }
    }

    @When("I iterate through the list to add all the even numbers")
    public void iIterateThroughTheListToAddAllTheEvenNumbers() {
        calculator.sumEvenNumbers();
    }

    @Then("the result should be {int}")
    public void theResultShouldBeResult(int expected) {
        Assertions.assertEquals(expected, actual);
    }
    @Then("a DivideByZeroException should be thrown")
    public void aDivideByZeroExceptionShouldBeThrown() {
        Assertions.assertInstanceOf(DivideByZeroException.class, exception);
    }

    @And("the exception should have the message {string}")
    public void theExceptionShouldHaveTheMessage(String expected) {
        Assertions.assertEquals(expected, exception.getMessage());
    }


}
