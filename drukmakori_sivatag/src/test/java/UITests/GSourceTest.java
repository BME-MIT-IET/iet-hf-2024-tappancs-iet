package UITests;

import drukmakori_sivatag.GSource;
import drukmakori_sivatag.Source;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class GSourceTest {

    @Test
    public void testIntersect_TargetInsideSource_ReturnsTrue() {
        Source source = new Source();
        GSource gSource = new GSource(source, 100, 100);

        int target_x = 110;
        int target_y = 110;

        boolean result = gSource.Intersect(target_x, target_y);

        assertTrue(result, "The target coordinates are inside the source");
    }

    @Test
    public void testIntersect_TargetOutsideSource_ReturnsFalse() {

        Source source = new Source();
        GSource gSource = new GSource(source, 100, 100);

        int target_x = 50;
        int target_y = 50;

        boolean result = gSource.Intersect(target_x, target_y);

        assertFalse(result, "The target coordinates are outside the source");
    }
}
