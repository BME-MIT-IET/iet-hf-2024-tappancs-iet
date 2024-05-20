package UITests;

import drukmakori_sivatag.GPump;
import drukmakori_sivatag.Pump;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GPumpTest {

    @Test
    public void testIntersect_TargetInsidePump_ReturnsTrue() {

        Pump pump = new Pump();
        GPump gPump = new GPump(pump, 10, 10);


        boolean result = gPump.Intersect(15, 15);


        assertTrue(result, "The target coordinates are inside the pump");
    }

    @Test
    public void testIntersect_TargetOutsidePump_ReturnsFalse() {

        Pump pump = new Pump();
        GPump gPump = new GPump(pump, 10, 10);


        boolean result = gPump.Intersect(60, 60);


        assertFalse(result, "The target coordinates are outside the pump");
    }

    @Test
    public void testIntersect_TargetOnEdgeOfPump_ReturnsTrue() {

        Pump pump = new Pump();
        GPump gPump = new GPump(pump, 10, 10);


        boolean result = gPump.Intersect(10, 10);


        assertTrue(result, "The target coordinates are on the edge of the pump");
    }

    @Test
    public void testIntersect_TargetOnCornerOfPump_ReturnsTrue() {

        Pump pump = new Pump();
        GPump gPump = new GPump(pump, 10, 10);


        boolean result = gPump.Intersect(50, 50);


        assertTrue(result, "The target coordinates are on the corner of the pump");
    }
}
