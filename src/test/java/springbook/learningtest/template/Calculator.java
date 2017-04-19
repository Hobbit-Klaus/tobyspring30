package springbook.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Hobbit-Klaus on 2017-04-19.
 */
class Calculator {
    Integer calcSum(String filepath) throws IOException {
        return lineReadTemplate(filepath, 0, new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        });
    }

    Integer calcMultiply(String filepath) throws IOException {
        return lineReadTemplate(filepath, 1, new LineCallback<Integer>() {
            @Override
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        });
    }

    String concatenate(String filepath) throws IOException {
        return lineReadTemplate(filepath, "", new LineCallback<String>() {
            @Override
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        });
    }

    private <T> T lineReadTemplate(String filepath, T initVal, LineCallback<T> callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
            String line;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
