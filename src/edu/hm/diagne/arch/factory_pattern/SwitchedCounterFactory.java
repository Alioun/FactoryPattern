package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.cs.rs.arch.a03_decorator.UCounter;
import edu.hm.ffrank.arch.decorator_pattern.ClockSecondCounter;
import edu.hm.ffrank.arch.decorator_pattern.LoopCounter;
import edu.hm.ffrank.arch.decorator_pattern.NaryCounter;

import java.util.NoSuchElementException;

/**
 * Created by diagne on 11/05/16.
 */
public class SwitchedCounterFactory extends CounterFactory {
    private static final int NARY_ARG_MIN = 2;
    private static final int NARY_ARG_MAX = 9;
    public Counter make(String typename, int... args) {
        Counter counter = null;
        switch (typename) {
            case "U":
            case "UCounter":
                counter = new UCounter();
                break;

            case "Loop":
            case "LoopCounter":
                if(args.length == 0){
                    throw new NoSuchElementException();
                }else{
                    counter = new LoopCounter(args);
                }
                break;

            case "Nary":
            case "NaryCounter":
                if(args[0] < NARY_ARG_MIN && args[0] > NARY_ARG_MAX){
                    throw new IllegalArgumentException("Number for NaryCounter not permitted: " + args[0]);
                }else{
                    counter = new NaryCounter(args[0]);
                }
                break;

            case "ClockSecond":
            case "ClockSecondCounter":
                counter = new ClockSecondCounter();
                break;

        }
        return counter;
    }

    public Counter make(Counter other, String typename, int arg) {
        return null;
    }
}
