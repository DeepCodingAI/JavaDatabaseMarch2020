package unittesting;

import org.testng.Assert;

public class TestCalculator {

    public static void main(String[] args) {
        Calculator cal = new Calculator();
        int additionResult = cal.addition(10,20);
        Assert.assertEquals(additionResult,30);
        int subtractionResult = cal.subtraction(100,50);
        Assert.assertEquals(subtractionResult,50);
        int multiplicationResult = cal.multiplication(100,200);
        Assert.assertEquals(multiplicationResult,20000);
    }
}
