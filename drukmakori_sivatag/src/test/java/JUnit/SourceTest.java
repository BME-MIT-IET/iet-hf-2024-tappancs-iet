package JUnit;

import drukmakori_sivatag.Pipe;
import drukmakori_sivatag.Source;
import org.junit.Test;
import static org.junit.Assert.*;

public class SourceTest {

    @Test
    public void TestSourceStep() {
        var source = new Source();
        var pipe1 = new Pipe();
        var pipe2 = new Pipe();

        source.AddNeighbour(pipe1);
        source.AddNeighbour(pipe2);

        source.Step();

        assertTrue("pipe1 must contain water", pipe1.isHasWater());
        assertTrue("pipe2 must contain water", pipe2.isHasWater());
    }

}
