package JUnit;

import drukmakori_sivatag.*;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MechanicTest {
    private Mechanic mechanic;
    private Drain drain;
    private Pump pump1;
    private Pump pump2;
    private Pipe pipe;

    @Before
    public void init(){
        mechanic = new Mechanic();
        drain = new Drain();
        pump1 = new Pump();
        pump2 = new Pump();
        pipe = new Pipe();
        pump1.ConnectPipe(pipe);
        pump2.ConnectPipe(pipe);
    }

    @Test
    public void testMechanicPlacesPump(){
        Pump controlledPump = new Pump();
        mechanic.SetControlledPump(controlledPump);
        mechanic.SetPosition(pipe);
        mechanic.PlacePump();

        assertNull(mechanic.GetControlledPump());
        assertEquals(mechanic.GetPosition(), controlledPump);
        assertTrue(pipe.GetNeighbors().contains(controlledPump));
        assertFalse(pipe.GetNeighbors().contains(pump2));
    }

    @Test
    public void testMechanicPlacesPipe(){
        Pipe controlledPipe = new Pipe();
        mechanic.SetControlledPipe(controlledPipe);
        mechanic.setNumberOfEndpoints(1);
        mechanic.SetPosition(pump1);
        mechanic.PlacePipe();

        assertTrue(pump1.GetNeighbors().contains(controlledPipe));
        assertTrue(controlledPipe.GetNeighbors().contains(pump1));
        assertNull(mechanic.GetControlledPipe());
    }

    @Test
    public void testMechanicPicksUpPipeAtDrain(){
        mechanic.SetPosition(drain);
        mechanic.PickUpPipe();

        assertNotNull(mechanic.GetControlledPipe());
        assertTrue(mechanic.GetControlledPipe().GetNeighbors().contains(drain));
    }

    @Test
    public void testMechanicPicksUpPipeAtPump(){
        mechanic.SetPosition(pump1);
        mechanic.PickUpPipe();

        assertNull(mechanic.GetControlledPipe());
    }

    @Test
    public void testMechanicPicksUpPumpAtDrain(){
        mechanic.SetPosition(drain);
        mechanic.PickUpPump();

        assertNotNull(mechanic.GetControlledPump());
    }

    @Test
    public void testMechanicPicksUpPumpAtPump(){
        mechanic.SetPosition(pump1);
        mechanic.PickUpPump();

        assertNull(mechanic.GetControlledPump());
    }

    @Test
    public void testMechanicFixesPipe(){
        pipe.setBroken(true);
        mechanic.SetPosition(pipe);
        mechanic.Fix();

        assertFalse(pipe.isBroken());
    }

    @Test
    public void testMechanicFixesPump(){
        pump1.SetIsBroken(true);
        mechanic.SetPosition(pump1);
        mechanic.Fix();

        assertFalse(pump1.isBroken());
    }

    @Test
    public void testMechanicChangesPumpWithoutNeighbors() {
        var pipe2 = new Pipe();
        mechanic.SetPosition(pump1);
        mechanic.ChangePump(pipe, pipe2);

        assertEquals("Source must be null without neighbor", null, pump1.GetSrc());
        assertEquals("Destination must be null without neighbor", null, pump1.GetDst());
    }

    @Test
    public void testMechanicChangesPumpWithNeighbors() {
        var pipe2 = new Pipe();
        mechanic.SetPosition(pump1);
        ArrayList<Field> neighbors = new ArrayList<>();
        neighbors.add(pipe);
        neighbors.add(pipe2);
        pump1.SetNeighbors(neighbors);
        mechanic.ChangePump(pipe, pipe2);

        assertEquals("Pipe must be the source", pipe, pump1.GetSrc());
        assertEquals("Pipe2 must be the destination", pipe2, pump1.GetDst());
    }
}
