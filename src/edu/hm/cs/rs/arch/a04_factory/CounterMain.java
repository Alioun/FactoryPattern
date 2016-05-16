/* (C) 2016, R. Schiedermeier, rs@cs.hm.edu
 * Oracle Corporation Java 1.8.0_72, Linux i386 4.4.0
 * emma (Intel Core i7-4790 CPU/3601 MHz, 8 Cores, 32000 MB RAM)
 **/
package edu.hm.cs.rs.arch.a04_factory;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.diagne.arch.factory_pattern.CounterFactory;

import java.util.stream.Stream;

/**
 * Testanwendung fuer eine einfache Counter-Factory.
 * Beispiel:
 * java CounterMain UCounter Limited/2
 * 0
 * 1
 * 2
 * 2
 * und so weiter
 *
 * @author R. Schiedermeier, rs@cs.hm.edu
 * @version 2016-04-28
 */
public class CounterMain {
    /** Factory fuer Counter. */
    // private static final SwitchedCounterFactory FACTORY = new SwitchedCounterFactory();
    private static final CounterFactory FACTORY = CounterFactory.get();

    /**
     * Entry point. Baut aus den Kommandozeilenargumenten einen Counter zusammen.
     * Fuehrt dann ein paar Operationen damit aus.
     *
     * @param args Kommandozeilenargumente:
     *             1. Typ eines elementaren Zaehlers und eine komma-getrennte Liste ganzer Zahlen,
     *             die an den betreffenden Konstruktor gehen.
     *             2. und weitere: Typ eines Filterzaehlers, Komma und eine ganze Zahl,
     *             die an den betreffenden Konstruktor geht.
     */
    public static void main(String... args) {
        testDrive(build(args));
    }

    /**
     * Baut aus den Strings einen dekorierten Zaehler zusammen.
     *
     * @param args Ein oder mehr Strings im Format Typname[,arg1,arg2,...].
     * @return Neuer Zaehler.
     */
    private static Counter build(String... args) {
        if(args == null || args.length == 0)
            throw new IllegalArgumentException("no args supplied");

        // Kommandozeilenargumente nacheinander analysieren und an die der Factory weitergeben ...
        Counter counter = null;                             // bisher aufgebauter Zaehler
        for(String arg: args) {
            final String[] token = arg.split("[\\W&&[^\\.]]+");       // String in Typ und Konstruktorargumente trennen
            final String typename = token[0];               // Typ des Zaehlers
            // int-Array mit den Zahlen nach dem Typnamen; eventuell leer
            final int[] numbers = Stream.of(token)
                    .skip(1)
                    .mapToInt(Integer::parseInt)
                    .toArray();
            counter = counter == null?
                    FACTORY.make(typename, numbers):            // Start mit einem elementaren Zaehler
                    FACTORY.make(counter, typename, numbers[0]); // Filterzaehler erzeugen und anfuegen
        }

        return counter;
    }

    /**
     * Einen Zaehler probefahren.
     * Gibt ein paar mal abwechselnd den Zaehlerstand aus und zaehlt weiter.
     *
     * @param counter Zaehler.
     */
    private static void testDrive(Counter counter) {
        assert counter != null;
        final int cyclesTotal = 10;
        for(int cycles = 0; cycles < cyclesTotal; cycles++) {
            System.out.println(counter.read());
            counter.tick();
        }
    }
}