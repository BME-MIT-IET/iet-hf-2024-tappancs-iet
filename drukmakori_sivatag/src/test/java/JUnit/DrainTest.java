package JUnit;

import drukmakori_sivatag.Drain;
import org.junit.Test;

import static org.junit.Assert.*;

public class DrainTest {

    private Drain drain;

    @Test
    public void testRequestPipeWithoutPipe() {
        drain = new Drain();
        drain.SetPipeCounter(0);

        var result = drain.RequestPipe();

        assertNull("No pipe should be returned", result);
    }

    @Test
    public void testRequestPipeWithPipe() {
        drain = new Drain();
        drain.SetPipeCounter(1);

        var result = drain.RequestPipe();

        assertNotNull("Pipe should be returned", result);
    }

    @Test
    public void testStepGeneratePipe() {
        drain = new Drain();
        drain.setTurnsUntilGenPipe(0);
        drain.Step();
        assertEquals("Pipe should be generated", 2, drain.getPipeCounter());
    }

    @Test
    public void testStepGenerateNoPipe() {
        drain = new Drain();
        drain.Step();
        assertEquals("Pipe shouldn't be generated", 1, drain.getPipeCounter());
    }
}
