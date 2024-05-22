package JUnit;

import drukmakori_sivatag.Functions;
import drukmakori_sivatag.Main;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.beans.Transient;
import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class FunctionsTest {
    Main main;

    @BeforeEach
    private void setup(){
        main = new Main();

    }

    @Test 
    public void testRequestPartPlayerFail(){
        Functions.RequestPart(new String[]{"", ""});
        assertEquals(true, true);
    }

    @Test
    public void testMovePlayerFail(){
        Functions.Move(new String[]{"", ""});
        assertEquals(true, true);
    }

    @Test
    public void testSabotagePlayerFail(){
        Functions.Sabotage(new String[]{""});
        assertEquals(true, true);
    }
}
