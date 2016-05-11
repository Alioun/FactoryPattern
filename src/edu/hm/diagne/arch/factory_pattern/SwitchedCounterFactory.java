package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.cs.rs.arch.a03_decorator.UCounter;
import edu.hm.ffrank.arch.decorator_pattern.*;

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
        Counter counter = null;
        switch (typename){
            case "Print":
            case "PrintCounter":
                counter = new PrintCounter(other, (char)arg);
                break;

            case "Shifted":
            case "ShiftedCounter":
                counter = new ShiftedCounter(other, arg);
                break;

            case "Jump":
            case "JumpCounter":
                counter = new JumpCounter(other, arg);
                break;

            case "Limited":
            case "LimitedCounter":
                counter = new LimitedCounter(other, arg);
                break;

            case "Multi":
            case "MultiCounter":
                counter = new MultiCounter(other, arg);
                break;

            //Selected counter not possible because of predicate
            case "Selected":
            case "SelectedCounter":
                //counter = new SelectedCounter(other, arg);
                break;
        }
        return counter;
    }
}
