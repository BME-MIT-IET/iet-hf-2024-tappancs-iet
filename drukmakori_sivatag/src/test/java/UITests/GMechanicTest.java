package UITests;

import drukmakori_sivatag.GMechanic;
import drukmakori_sivatag.Mechanic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

public class GMechanicTest {

    private GMechanic gMechanic;
    private Mechanic mechanic;
    private BufferedImage canvas;
    private Graphics2D graphics;

    @BeforeEach
    public void setUp() {
        mechanic = new Mechanic();
        gMechanic = new GMechanic(mechanic, 50,50);
        canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        graphics = canvas.createGraphics();
    }


    @Test
    public void testIntersect() {


        assertThat(gMechanic.Intersect(50, 40)).isFalse();
        assertThat(gMechanic.Intersect(55, 35)).isFalse();
        assertThat(gMechanic.Intersect(60, 45)).isTrue();

        assertThat(gMechanic.Intersect(30, 30)).isFalse();
        assertThat(gMechanic.Intersect(70, 70)).isFalse();
    }

    @Test
    public void testGetRef() {

        assertThat(gMechanic.getRef()).isEqualTo(mechanic);
    }
}
