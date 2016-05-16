package edu.hm.diagne.arch.factory_pattern.Tests;

import edu.hm.cs.rs.arch.a03_decorator.Counter;
import edu.hm.diagne.arch.factory_pattern.SwitchedCounterFactory;
import edu.hm.ffrank.arch.decorator_pattern.LoopCounter;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * Created by florianfrank on 16.05.16.
 */
@SuppressWarnings("PMD")
public class FactoryPatternTest  {

    @Test
    public void swichtedElementaryCounterTest(){
        SwitchedCounterFactory switchedCounterFactory = new SwitchedCounterFactory();
        Counter ucounter =  switchedCounterFactory.make("U");
        Counter loopCounter = switchedCounterFactory.make("Loop",1,2,3);
        Counter naryCounter = switchedCounterFactory.make("Nary",3);
        Counter clockSecondCounter = switchedCounterFactory.make("ClockSecond");

            //UCounter Tests
            for(int i = 0 ; i< 10; i++){
                assertEquals(i,ucounter.read());
                ucounter.tick();
            }

            //LoopCounter Tests
            assertEquals(1,loopCounter.read());
            assertEquals(2,loopCounter.tick().read());
            assertEquals(3,loopCounter.tick().read());
            assertEquals(1,loopCounter.tick().read());
            assertEquals(2,loopCounter.tick().read());
            assertEquals(3,loopCounter.tick().read());

            //NaryCounter Tests
            assertEquals(0,naryCounter.read());
            assertEquals(1,naryCounter.tick().read());
            assertEquals(2,naryCounter.tick().read());
            assertEquals(10,naryCounter.tick().read());
            assertEquals(11,naryCounter.tick().read());
            assertEquals(12,naryCounter.tick().read());
            assertEquals(20,naryCounter.tick().read());

            //ClockSecondCounter Tests
            assertEquals(Calendar.getInstance().get(Calendar.SECOND),clockSecondCounter.read());
            assertEquals(Calendar.getInstance().get(Calendar.SECOND)+1,clockSecondCounter.tick().read());
    }

    @Test
    public void switchedCounterFiltertest(){
        SwitchedCounterFactory switchedCounterFactory = new SwitchedCounterFactory();
        //Elementary Counters
        Counter ucounter = switchedCounterFactory.make("U");
        Counter loopCounter = switchedCounterFactory.make("Loop",1,2,3);
        Counter naryCounter = switchedCounterFactory.make("Nary",3);
        Counter clockSecondCounter = switchedCounterFactory.make("ClockSecond");

        //Filterclasses
        Counter printUCounter = switchedCounterFactory.make(ucounter,"Print",'a');
        Counter printLoopCounter = switchedCounterFactory.make(loopCounter,"Print",'a');
        Counter printNaryCounter = switchedCounterFactory.make(naryCounter,"Print",'a');
        Counter printClockSecondCounter = switchedCounterFactory.make(clockSecondCounter,"Print",'a');

        //printCounterTests
        final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        printUCounter.tick();
        assertEquals(0+"a",outContent.toString());
        outContent.reset();
        printUCounter.tick();
        assertEquals(1+"a",outContent.toString());
        outContent.reset();
        printUCounter.tick();
        assertEquals(2+"a",outContent.toString());
        outContent.reset();
        printUCounter.tick();
        assertEquals(3+"a",outContent.toString());
        outContent.reset();
        printLoopCounter.tick();
        assertEquals(1+"a",outContent.toString());
        outContent.reset();
        printLoopCounter.tick();
        assertEquals(2+"a",outContent.toString());
        outContent.reset();
        printLoopCounter.tick();
        assertEquals(3+"a",outContent.toString());
        outContent.reset();
        printLoopCounter.tick();
        assertEquals(1+"a",outContent.toString());
        outContent.reset();
        printNaryCounter.tick();
        assertEquals(0+"a",outContent.toString());
        outContent.reset();
        printNaryCounter.tick();
        assertEquals(1+"a",outContent.toString());
        outContent.reset();
        printNaryCounter.tick();
        assertEquals(2+"a",outContent.toString());
        outContent.reset();
        printNaryCounter.tick();
        assertEquals(10+"a",outContent.toString());
        outContent.reset();
        printClockSecondCounter.tick();
       // assertEquals(Calendar.getInstance().get(Calendar.SECOND)+"a",outContent.toString());
        outContent.reset();

        //shiftedCounterTests
        ucounter = switchedCounterFactory.make("U");
        loopCounter = switchedCounterFactory.make("Loop",1,2,3);
        naryCounter = switchedCounterFactory.make("Nary",3);
        Counter shiftedUCounter = switchedCounterFactory.make(ucounter,"Shifted",2);
        Counter shiftedLoopCounter = switchedCounterFactory.make(loopCounter,"Shifted",2);
        Counter shiftedNaryCounter = switchedCounterFactory.make(naryCounter,"Shifted",2);
        Counter shiftedClockSecondCounter = switchedCounterFactory.make(clockSecondCounter,"Shifted",2);
        assertEquals(2,shiftedUCounter.read());
        assertEquals(3,shiftedUCounter.tick().read());
        assertEquals(4,shiftedUCounter.tick().read());
        assertEquals(3,shiftedLoopCounter.read());
        assertEquals(4,shiftedLoopCounter.tick().read());
        assertEquals(5,shiftedLoopCounter.tick().read());
        assertEquals(3,shiftedLoopCounter.tick().read());
        assertEquals(2,shiftedNaryCounter.read());
        assertEquals(3,shiftedNaryCounter.tick().read());
        assertEquals(4,shiftedNaryCounter.tick().read());
        assertEquals(12,shiftedNaryCounter.tick().read());
        assertEquals(Calendar.getInstance().get(Calendar.SECOND)+2,shiftedClockSecondCounter.read());

        //JumpCounterTests
        ucounter = switchedCounterFactory.make("U");
        loopCounter = switchedCounterFactory.make("Loop",1,2,3);
        naryCounter = switchedCounterFactory.make("Nary",3);
        Counter jumpUCounter = switchedCounterFactory.make(ucounter,"Jump",2);
        Counter jumpLoopCounter = switchedCounterFactory.make(loopCounter,"Jump",2);
        Counter jumpNaryCounter = switchedCounterFactory.make(naryCounter,"Jump",2);
        Counter jumpClockSecondCounter = switchedCounterFactory.make(clockSecondCounter,"Jump",2);
        assertEquals(0,jumpUCounter.read());
        assertEquals(2,jumpUCounter.tick().read());
        assertEquals(4,jumpUCounter.tick().read());
        assertEquals(1,jumpLoopCounter.read());
        assertEquals(3,jumpLoopCounter.tick().read());
        assertEquals(2,jumpLoopCounter.tick().read());
        assertEquals(1,jumpLoopCounter.tick().read());
        assertEquals(0,jumpNaryCounter.read());
        assertEquals(2,jumpNaryCounter.tick().read());
        assertEquals(11,jumpNaryCounter.tick().read());
        assertEquals(20,jumpNaryCounter.tick().read());
        assertEquals(Calendar.getInstance().get(Calendar.SECOND),jumpClockSecondCounter.read());
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,2);
        assertEquals(cal.get(Calendar.SECOND),jumpClockSecondCounter.tick().read());
        cal = Calendar.getInstance();
        cal.add(Calendar.SECOND,2);
        assertEquals(cal.get(Calendar.SECOND),jumpClockSecondCounter.tick().read());

        //multiCounterTests
        ucounter = switchedCounterFactory.make("U");
        loopCounter = switchedCounterFactory.make("Loop",1,2,3);
        naryCounter = switchedCounterFactory.make("Nary",3);
        Counter multiUCounter = switchedCounterFactory.make(ucounter,"Multi",2);
        Counter multiLoopCounter = switchedCounterFactory.make(loopCounter,"Multi",2);
        Counter multiNaryCounter = switchedCounterFactory.make(naryCounter,"Multi",2);
        Counter multiClockSecondCounter = switchedCounterFactory.make(clockSecondCounter,"Multi",2);
        assertEquals(0,multiUCounter.read());
        assertEquals(0,multiUCounter.tick().read());
        assertEquals(1,multiUCounter.tick().read());
        assertEquals(1,multiLoopCounter.read());
        assertEquals(1,multiLoopCounter.tick().read());
        assertEquals(2,multiLoopCounter.tick().read());
        assertEquals(2,multiLoopCounter.tick().read());
        assertEquals(3,multiLoopCounter.tick().read());
        assertEquals(3,multiLoopCounter.tick().read());
        assertEquals(1,multiLoopCounter.tick().read());
        assertEquals(1,multiLoopCounter.tick().read());
        assertEquals(0,multiNaryCounter.read());
        assertEquals(0,multiNaryCounter.tick().read());
        assertEquals(1,multiNaryCounter.tick().read());
        assertEquals(1,multiNaryCounter.tick().read());
        assertEquals(2,multiNaryCounter.tick().read());
        assertEquals(2,multiNaryCounter.tick().read());
        assertEquals(10,multiNaryCounter.tick().read());
        assertEquals(10,multiNaryCounter.tick().read());
        int second = multiClockSecondCounter.read();
        assertEquals(second,multiClockSecondCounter.tick().read());
        second = multiClockSecondCounter.tick().read();
        assertEquals(second,multiClockSecondCounter.tick().read());
    }

    @Test(expected = IllegalArgumentException.class)
    public void switchedCounterFalseClassNameExceptionTest(){
        SwitchedCounterFactory switchedCounterFactory = new SwitchedCounterFactory();
        Counter counter = switchedCounterFactory.make("Not available Counter");
    }

    @Test(expected = IllegalArgumentException.class)
    public void switchedLoopCounterFalseNumberOfArgumentsExceptionTest(){
        SwitchedCounterFactory switchedCounterFactory = new SwitchedCounterFactory();
        switchedCounterFactory.make("Loop");
    }

    @Test(expected = IllegalArgumentException.class)
    public void switchedNaryCounterFalseNumberOfArgumentsExceptionTest(){
        SwitchedCounterFactory switchedCounterFactory = new SwitchedCounterFactory();
        switchedCounterFactory.make("Nary");
    }


}
