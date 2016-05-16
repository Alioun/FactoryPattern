/**
 * Organisation: Hochschule München
 * Java: Version 1.8
 *
 * @version 12.05.2016
 * @author Florian Frank, Alioun Diagne
 */
package edu.hm.diagne.arch.factory_pattern;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.cs.rs.arch.a03_decorator.UCounter;
import edu.hm.ffrank.arch.decorator_pattern.LoopCounter;
import edu.hm.ffrank.arch.decorator_pattern.NaryCounter;
import edu.hm.ffrank.arch.decorator_pattern.ClockSecondCounter;
import edu.hm.ffrank.arch.decorator_pattern.PrintCounter;
import edu.hm.ffrank.arch.decorator_pattern.ShiftedCounter;
import edu.hm.ffrank.arch.decorator_pattern.JumpCounter;
import edu.hm.ffrank.arch.decorator_pattern.LimitedCounter;
import edu.hm.ffrank.arch.decorator_pattern.MultiCounter;

/**
 * This class makes decorated or basic Counters.
 */
public class SwitchedCounterFactory extends CounterFactory {
    /**
     * Low end of the range allowed for the NaryCounter.
     */
    private static final int NARY_ARG_MIN = 2;
    /**
     * High end of the range allowed for the NaryCounter.
     */
    private static final int NARY_ARG_MAX = 9;
    /**
     * Counter string because there were too many instances of it before.
     */
    private static final String COUNTER_STRING = "Counter";

    @Override
    public Counter make(String typename, int... args) {
        Counter counter = null;
        String counterName = typename;
        if (typename.contains(COUNTER_STRING)) {
            counterName = typename.substring(0, typename.indexOf(COUNTER_STRING));
        }
        switch (counterName) {
            case "U":
                counter = new UCounter();
                break;

            case "Loop":
                if (args.length == 0) {
                    throw new IllegalArgumentException("Loopcounter expects at least one parameter");
                } else {
                    counter = new LoopCounter(args);
                }
                break;

            case "Nary":
                if(args.length != 1){
                    throw new IllegalArgumentException("Narycounter exactly one Parameter");
                }else {
                    if (args[0] < NARY_ARG_MIN || args[0] > NARY_ARG_MAX) {
                        throw new IllegalArgumentException("Number for NaryCounter not permitted: " + args[0]);
                    } else {
                        counter = new NaryCounter(args[0]);
                    }
                }

                break;

            case "ClockSecond":
                counter = new ClockSecondCounter();
                break;
            default:
                throw new IllegalArgumentException("Counter"+typename+"doesn't exists");

        }
        return counter;
    }

    @Override
    public Counter make(Counter other, String typename, int arg) {
        Counter counter = null;
        String counterName = typename;
        if (typename.contains(COUNTER_STRING)) {
            counterName = typename.substring(0, typename.indexOf(COUNTER_STRING));
        }
        switch (counterName) {
            case "Print":
                counter = new PrintCounter(other, (char) arg);
                break;

            case "Shifted":
                counter = new ShiftedCounter(other, arg);
                break;

            case "Jump":
                counter = new JumpCounter(other, arg);
                break;

            case "Limited":
                counter = new LimitedCounter(other, arg);
                break;

            case "Multi":
                counter = new MultiCounter(other, arg);
                break;
            default:
                counter = null;
                break;
        }
        return counter;
    }
}
