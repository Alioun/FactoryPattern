package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.ffrank.arch.decorator_pattern.LoopCounter;

/**
 * Created by diagne on 11/05/16.
 */
public class FakeCounterFactory extends CounterFactory {

    public Counter make(String typename, int... args){
        return new LoopCounter(0);
    }

    public Counter make(Counter other, String typename, int arg) {
        return new LoopCounter(0);
    }
}
