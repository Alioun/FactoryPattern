package edu.hm.diagne.arch.factory_pattern;

/**
 * Created by diagne on 11/05/16.
 */
public class CounterFactory {
    private static CounterFactory instance = null;

    public static CounterFactory get() {
        String property = System.getProperty("factory.type");
        if (instance == null) {
            if ("Switched".equals(property) || "SwitchedCounterFactory".equals(property)) {
                instance = new SwitchedCounterFactory();
            } else if ("Fake".equals(property) || "FakeCounterFactory".equals(property)) {
                instance = new FakeCounterFactory();
            } else {
                throw new IllegalArgumentException();
            }
        }
        return instance;
    }
}
