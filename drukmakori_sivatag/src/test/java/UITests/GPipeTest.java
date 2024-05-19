package UITests;

import static org.junit.jupiter.api.Assertions.*;

import drukmakori_sivatag.GPipe;
import drukmakori_sivatag.Pipe;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GPipeTest {

    @Test
    public void testSetAndGetCenterx() {
        // Arrange
        GPipe gPipe = new GPipe(new Pipe());
        int expectedCenterx = 100;

        // Act
        gPipe.setCenterx(expectedCenterx);
        int actualCenterx = gPipe.getCenterx();

        // Assert
        assertEquals(expectedCenterx, actualCenterx);
    }
}


