package com.sparta.kch;

import com.sparta.kch.Exceptions.DivideByZeroException;

import java.util.List;

public class Calculator {

    private Integer num1;
    private Integer num2;
    //array of numbers
    private List<Integer> numbers;

    public void setNumbers(List<Integer> numbers) {
        this.numbers = numbers;
    }

    public void setNum1(int num1) {
        this.num1 = num1;
    }

    public void setNum2(int num2) {
        this.num2 = num2;
    }

    public Integer add() {
        return num1 + num2;
    }
    public Integer subtract() {
        return num1 - num2;
    }
    public Integer multiply() {
        return num1 * num2;
    }
    public Integer divide() throws DivideByZeroException {
       if(num2 == 0){
           throw new DivideByZeroException("Cannot Divide By Zero");
       }
       return num1 / num2;
    }

    public Integer sumEvenNumbers() {
        int sum = 0;
        for (int number : numbers) {
            if (number % 2 == 0) {
                sum += number;
            }
        }
        return sum;
    }
}
