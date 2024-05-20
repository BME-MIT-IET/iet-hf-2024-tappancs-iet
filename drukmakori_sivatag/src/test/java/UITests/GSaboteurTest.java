package UITests;

import drukmakori_sivatag.GSaboteur;
import drukmakori_sivatag.Saboteur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GSaboteurTest {

    @Test
    public void testIntersect_TargetInsideSaboteur_ReturnsTrue() {
        Saboteur saboteur = new Saboteur();
        GSaboteur gSaboteur = new GSaboteur(saboteur, 100, 100);

        int target_x = 105;
        int target_y = 95;

        boolean result = gSaboteur.Intersect(target_x, target_y);

        assertTrue(result, "The target coordinates are inside the saboteur");
    }

    @Test
    public void testIntersect_TargetOutsideSaboteur_ReturnsFalse() {

        Saboteur saboteur = new Saboteur();
        GSaboteur gSaboteur = new GSaboteur(saboteur, 10, 10);


        boolean result = gSaboteur.Intersect(50, 50);


        assertFalse(result, "The target coordinates are outside the saboteur");
    }

    @Test
    public void testIntersect_TargetOnEdgeOfSaboteur_ReturnsTrue() {

        Saboteur saboteur = new Saboteur();
        GSaboteur gSaboteur = new GSaboteur(saboteur, 100, 100);

        int target_x = 100;
        int target_y = 90;

        boolean result = gSaboteur.Intersect(target_x, target_y);

        assertTrue(result, "The target coordinates are on the edge of the saboteur");
    }


}
