/**
 * Organisation: Hochschule München
 * Java: Version 1.8
 *
 * @version 12.05.2016
 * @author Florian Frank, Alioun Diagne
 */
package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;

import java.util.HashMap;
import java.util.Map;

/**
 * Produces CounterFactory of two types, FakeCounterFactory and SwitchedCounterFactory.
 */
public abstract class CounterFactory {
    /**
     * Map used for Pooling, so that only one factory of each type gets made.
     */
    private static final Map<String, CounterFactory> FACTORY_INSTANCES = new HashMap<String, CounterFactory>();

    /**
     * Makes a factory corresponding to the type stated in the Systemproperty factory.type.
     * Only ever makes one instance of each factory.
     *
     * @return Made or existing factory.
     */
    public static CounterFactory get() {
        String factoryType = System.getProperty("factory.type");

        if (factoryType == null) {
            throw new NullPointerException("Systemproperty factory.type not specified");
        } else {
            if (factoryType.contains("CounterFactory")) {
                factoryType = factoryType.substring(0, factoryType.indexOf("CounterFactory"));
            }
        }

        final CounterFactory instance;
        switch (factoryType) {

            case "Switched":
                if (FACTORY_INSTANCES.get(factoryType) == null) {
                    FACTORY_INSTANCES.put(factoryType, new SwitchedCounterFactory());
                }
                instance = FACTORY_INSTANCES.get(factoryType);
                break;

            case "Fake":
                if (FACTORY_INSTANCES.get(factoryType) == null) {
                    FACTORY_INSTANCES.put(factoryType, new FakeCounterFactory());
                }
                instance = FACTORY_INSTANCES.get(factoryType);
                break;

            default:
                throw new IllegalArgumentException("Unknown factory type: " + factoryType);
        }
        return instance;
    }

    /**
     * Makes a basic counters.
     *
     * @param typename Name of the Counter to make.
     * @param args     List of arguments to pass over for the counters construction.
     * @return New and fresh Counter.
     */
    public abstract Counter make(String typename, int... args);

    /**
     * Makes a decorated counter.
     *
     * @param other    Other counter to decorate with.
     * @param typename Name of the counter that gets decorated.
     * @param arg      Argument for the second parameter of the filer Counter.
     * @return A new decorated counter.
     */
    public abstract Counter make(Counter other, String typename, int arg);
}
