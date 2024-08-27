package com.sparta.kch;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;

public class CalculatorStepDefs {

    private Calculator calculator;
    private Integer actual;

    @Given("I have a calculator")
    public void iHaveACalculator() {
        calculator = new Calculator();
    }

    @And("I enter {int} and {int} into the calculator")
    public void iEnterAndIntoTheCalculator(int arg0, int arg1) {
        calculator.setNum1(arg0);
        calculator.setNum2(arg1);
    }

    @When("I press add")
    public void iPressAdd() {
        actual = calculator.add();
    }

    @When("I press subtract")
    public void iPressSubtract() {
        actual = calculator.subtract();
    }

    @Then("the result should be {int}")
    public void theResultShouldBeResult(int expected) {
        Assertions.assertEquals(expected, actual);
    }

}
