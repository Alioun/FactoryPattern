package edu.hm.diagne.arch.factory_pattern;

/**
 * Created by diagne on 11/05/16.
 */
public class CounterFactory {
    private static CounterFactory instance = null;

    public static CounterFactory get() {
        final String factoryType = System.getProperty("factory.type");

        if (instance == null) {

            switch (factoryType) {

                case "Switched":
                case "SwitchedCounterFactory":
                    instance = new SwitchedCounterFactory();
                    break;

                case "Fake":
                case "FakeCounterFactory":
                    instance = new FakeCounterFactory();
                    break;

                default:
                    throw new IllegalArgumentException("unknown factory type: " + factoryType);
            }
        }
        return instance;
    }
}
