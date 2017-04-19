package springbook.learningtest.template;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Hobbit-Klaus on 2017-04-19.
 */
public class CalcSumTest {
    private Calculator calculator = null;
    private String filepath = null;

    @Before
    public void setUp() {
        calculator = new Calculator();
        filepath = getClass().getResource("/numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(filepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(filepath), is(24));
    }

    @Test
    public void concatenateStrings() throws IOException {
        assertThat(calculator.concatenate(filepath), is("1234"));
    }
}
