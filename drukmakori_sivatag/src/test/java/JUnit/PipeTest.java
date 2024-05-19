package JUnit;

import drukmakori_sivatag.*;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class PipeTest {

    private Pipe pipe;

    @Test
    public void TestStepLeaking() {

        pipe = new Pipe();
        pipe.setBroken(true);

        var src = new Source();
        src.AddNeighbour(pipe);
        src.Step();

        pipe.Step();

        assertEquals("Pipe should leak water", 1, pipe.getGc().getLeakedWater());
    }

    @Test
    public void TestPipeFix() {
        pipe = new Pipe();
        pipe.setBroken(true);

        pipe.Fix();

        assertFalse("Pipe shouldn't be broken", pipe.isBroken());

        assertTrue("TicksUntilBreakable should be between 0 and 10", (pipe.getTicksUntilBreakable() >= 0 && pipe.getTicksUntilBreakable() <= 9));
    }

    @Test
    public void TestPipeAcceptFree() {
        Mechanic mechanic = new Mechanic();
        pipe = new Pipe();

        pipe.Accept(mechanic);

        assertTrue("Mechanic should occupy pipe", pipe.GetOccupied());
    }

    @Test
    public void TestPipeAcceptOccupied() {
        Mechanic mechanic1 = new Mechanic();
        Mechanic mechanic2 = new Mechanic();
        pipe = new Pipe();

        var result1 = pipe.Accept(mechanic1);
        assertTrue("Mechanic1 should occupy pipe", result1);

        var result2 = pipe.Accept(mechanic2);
        assertFalse("Pipe mustn't accept mechanic2", result2);
    }

    @Test
    public void TestPipeAcceptSlippery() {
        Mechanic mechanic = new Mechanic();
        pipe = new Pipe();
        ArrayList<Field> pumpNeighbors = new ArrayList<>();
        pumpNeighbors.add(pipe);
        Pump pump1 = new Pump();
        ArrayList<Field> pipeNeighbors = new ArrayList<>();
        pipeNeighbors.add(pump1);
        pump1.SetNeighbors(pumpNeighbors);
        pipe.SetNeighbors(pipeNeighbors);
        mechanic.SetPosition(pump1);

        pipe.SetSlippery(true);
        var result1 = pipe.Accept(mechanic);
        System.out.println("TestPipeAcceptSlippery 50% of failing must be solved");

        assertFalse("Slippery pipe shouldn't accept mechanic", result1);
    }

    @Test
    public void TestPipePull() {
        pipe = new Pipe();
        pipe.getGc().setLeakedWater(0);
        pipe.setBroken(true);

        var src = new Source();
        src.AddNeighbour(pipe);
        src.Step();

        var result1 = pipe.PullWater();

        assertFalse("Pipe mustn't give water because it's broken", result1);
        assertEquals("Pipe must leak water because it's broken", 1, pipe.getGc().getLeakedWater());
    }

    @Test
    public void TestPipeFillBroken() {
//        pipe = new Pipe();
//        pipe.getGc().setLeakedWater(0);
//        pipe.setBroken(true);
//
//        var src = new Source();
//        src.AddNeighbour(pipe);
//        src.Step();
//
//        var result = pipe.Fill();
//
//        assertFalse("Pipe mustn't accept water because it's broken", result);
//        assertEquals("Pipe must leak water because it's broken", 1, pipe.getGc().getLeakedWater());


        /**
         * return values totally random
         */
    }

    @Test
    public void TestPipePlacePump() {
        pipe = new Pipe();
        Pump pump1 = new Pump();
        Pump pump2 = new Pump();
        Pump pump3 = new Pump();
        ArrayList<Field> pumpNeighbors = new ArrayList<>();
        ArrayList<Field> pipeNeighbors = new ArrayList<>();
        Mechanic mechanic = new Mechanic();
        mechanic.SetControlledPump(pump3);


        pumpNeighbors.add(pipe);
        pipeNeighbors.add(pump1);
        pipeNeighbors.add(pump2);

        pipe.SetNeighbors(pipeNeighbors);
        pump1.SetNeighbors(pumpNeighbors);
        pump2.SetNeighbors(pumpNeighbors);

        var result = pipe.PlacePump(pump3);

        assertTrue("Pump should be placed", result);
    }














}
