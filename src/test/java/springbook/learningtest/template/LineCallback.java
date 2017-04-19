package springbook.learningtest.template;

/**
 * Created by Hobbit-Klaus on 2017-04-20.
 */
public interface LineCallback<T> {
    T doSomethingWithLine(String line, T value);
}
