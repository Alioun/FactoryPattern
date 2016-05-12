package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;

/**
 * Created by diagne on 11/05/16.
 */
public abstract class CounterFactory {
    private static CounterFactory instance = null;

    public static CounterFactory get() {
        String factoryType = System.getProperty("factory.type");

        if (factoryType.contains("CounterFactory")) {
            factoryType = factoryType.substring(0, factoryType.indexOf("CounterFactory"));
        }

        if (instance == null) {

            switch (factoryType) {

                case "Switched":
                    instance = new SwitchedCounterFactory();
                    break;

                case "Fake":
                    instance = new FakeCounterFactory();
                    break;

                default:
                    throw new IllegalArgumentException("unknown factory type: " + factoryType);
            }
        }
        return instance;
    }

    public abstract Counter make(String typename, int... args);

    public abstract Counter make(Counter other, String typename, int arg);
}
