package JUnit;

import drukmakori_sivatag.Pipe;
import drukmakori_sivatag.Saboteur;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SaboteurTest {
    private Saboteur saboteur;
    private Pipe pipe;

    @Before
    public void init(){
        saboteur = new Saboteur();
        pipe = new Pipe();
    }

    @Test
    public void testSaboteurMakesPipeSlippery(){
        saboteur.SetPosition(pipe);
        saboteur.MakeSlippery();
        assertTrue(pipe.GetSlippery());
    }

}
