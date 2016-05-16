/* (C) 2016, R. Schiedermeier, rs@cs.hm.edu
 * Oracle Corporation Java 1.8.0_72, Linux i386 4.3.0
 * violet (Intel Core i7 CPU 920/2668 MHz, 8 Cores, 12032 MB RAM)
 **/
package edu.hm.cs.rs.arch.a03_decorator;

/**
 * Elementarer Zaehler, der ab 0 in Einerschritten hochzaehlt.
 *
 * @author Reinhard Schiedermeier, rs@cs.hm.edu
 * @version 2012-04-02
 */
public class UCounter implements Counter {
    /** Aktueller Zaehlerstand. */
    private int currentValue = 0;

    @Override
    public int read() {
        return currentValue;
    }

    /**
     * Zaehlt um 1 hoch.
     *
     * @return Dieser Zaehler.
     */
    @Override
    public UCounter tick() {
        currentValue++;
        return this;
    }

}