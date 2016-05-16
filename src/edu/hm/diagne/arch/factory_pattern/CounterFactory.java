/**
 * Organisation: Hochschule München
 * Java: Version 1.8
 *
 * @version 12.05.2016
 * @author Florian Frank, Alioun Diagne
 */
package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;

/**
 * Produces CounterFactory of two types, FakeCounterFactory and SwitchedCounterFactory.
 */
public abstract class CounterFactory {
    /**
     * Object to guarantee only one FakeCounterFactory gets made.
     */
    private static CounterFactory fakeFactoryInstance;

    /**
     * Object to guarantee only one SwitchedCounterFactory gets made.
     */
    private static CounterFactory switchedFactoryInstance;

    /**
     * Makes a factory corresponding to the type stated in the Systemproperty factory.type.
     * Only ever makes one instance of each factory.
     * @return Made or existing factory.
     */
    public static CounterFactory get() {
        String factoryType = System.getProperty("factory.type");
        final CounterFactory instance;

        if (factoryType.contains("CounterFactory")) {
            factoryType = factoryType.substring(0, factoryType.indexOf("CounterFactory"));
        }

            switch (factoryType) {

                case "Switched":
                    if(switchedFactoryInstance == null) {
                        switchedFactoryInstance = new SwitchedCounterFactory();
                    }
                    instance = switchedFactoryInstance;
                    break;

                case "Fake":
                    if(fakeFactoryInstance == null) {
                        fakeFactoryInstance = new FakeCounterFactory();
                    }
                    instance = fakeFactoryInstance;
                    break;

                default:
                    throw new IllegalArgumentException("unknown factory type: " + factoryType);
            }
        return instance;
    }
    /**
     * Makes a basic counters.
     * @param typename Name of the Counter to make.
     * @param args List of arguments to pass over for the counters construction.
     * @return New and fresh Counter.
     */
    public abstract Counter make(String typename, int... args);

    /**
     * Makes a decorated counter.
     * @param other Other counter to decorate with.
     * @param typename Name of the counter that gets decorated.
     * @param arg Argument for the second parameter of the filer Counter.
     * @return A new decorated counter.
     */
    public abstract Counter make(Counter other, String typename, int arg);
}
