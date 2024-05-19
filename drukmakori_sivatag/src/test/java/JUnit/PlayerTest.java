package JUnit;

import drukmakori_sivatag.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class PlayerTest {
    private Player player;
    private Field node1;
    private Field node2;
    private Pipe pipe;

    @Before
    public void init(){
        player = new Mechanic();
        node1 = new Pump();
        node2 = new Pump();
        pipe = new Pipe();
        node1.ConnectPipe(pipe);
        node2.ConnectPipe(pipe);
    }

    @Test
    public void testPlayerMoves(){
        player.SetPosition(pipe);
        player.MoveTo(node1);
        assertEquals(player.GetPosition(), node1);
    }

    @Test
    public void testPlayerMovesFromStickyPipe(){
        player.SetPosition(pipe);
        player.MakeSticky();
        player.MoveTo(node1);
        player.MoveTo(pipe);
        player.MoveTo(node2);
        assertEquals(player.GetPosition(), pipe);
    }

    @Test
    public void testPlayerMovesToSlipperyPipe(){
        pipe.SetSlippery(true);
        player.SetPosition(node1);
        player.MoveTo(pipe);
        assertNotEquals(player.GetPosition(), pipe);
    }

    @Test
    public void testPlayerBreaksPipe(){
        player.SetPosition(pipe);
        player.Sabotage();
        assertFalse(pipe.isBroken());
    }

    @Test
    public void testPlayerMakesPipeSticky(){
        player.SetPosition(pipe);
        player.MakeSticky();
        assertTrue(pipe.IsSticky());
    }
}
