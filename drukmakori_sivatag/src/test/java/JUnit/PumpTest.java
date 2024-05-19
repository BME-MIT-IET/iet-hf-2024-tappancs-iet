package JUnit;

import drukmakori_sivatag.Field;
import drukmakori_sivatag.Pipe;
import drukmakori_sivatag.Pump;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class PumpTest {

    private Pump pump;

    @Test
    public void TestPumpStepDestinationFill() {
        pump = new Pump();
        pump.SetBuffer(1);
        Pipe pipe = new Pipe();
        pump.SetDst(pipe);
        pump.Step();

        assertEquals("Buffer must be emptied", 0, pump.getBuffer());
    }

    @Test
    public void TestPumpStepSourcePull() {
        pump = new Pump();
        pump.SetBuffer(0);
        Pipe pipe = new Pipe();
        pump.SetSrc(pipe);
        pipe.SetHasWater(true);
        pump.Step();

        assertEquals("Buffer must have water", 1, pump.getBuffer());
    }

    @Test
    public void TestPumpChange() {
        pump = new Pump();
        Pipe pipe1 = new Pipe();
        Pipe pipe2 = new Pipe();
        ArrayList<Field> pumpNeighbors = new ArrayList<>();
        pumpNeighbors.add(pipe1);
        pumpNeighbors.add(pipe2);
        pump.SetNeighbors(pumpNeighbors);

        pump.SetSrc(pipe1);
        pump.SetDst(pipe2);

        pump.Change(pipe2, pipe1);

        assertEquals("Pump src must be pipe2", pipe2, pump.GetSrc());
        assertEquals("Pump dst must be pipe1", pipe1, pump.GetDst());
    }

    @Test
    public void TestPumpFix() {
        pump = new Pump();
        pump.setBroken(true);

        pump.Fix();

        assertFalse("Pump must be working", pump.isBroken());
    }
}
