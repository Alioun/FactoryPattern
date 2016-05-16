/**
 * Organisation: Hochschule München
 * Java: Version 1.8
 *
 * @version 12.05.2016
 * @author Florian Frank, Alioun Diagne
 */
package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.ffrank.arch.decorator_pattern.LoopCounter;

/**
 * This class makes only one FakeCounter that does nothing and always returns that one counter when asked.
 */
public class FakeCounterFactory extends CounterFactory {
    /**
     * Counter that always reads 0.
     */
    private static final Counter FAKE_COUNTER = new LoopCounter(0);

    @Override
    public Counter make(String typename, int... args) {
        return FAKE_COUNTER;
    }

    @Override
    public Counter make(Counter other, String typename, int arg) {
        return FAKE_COUNTER;
    }
}
