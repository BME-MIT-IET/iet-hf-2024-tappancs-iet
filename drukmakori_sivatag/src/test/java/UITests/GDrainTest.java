package UITests;

import drukmakori_sivatag.Drain;
import drukmakori_sivatag.GDrain;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.awt.image.BufferedImage;

import static org.assertj.core.api.Assertions.assertThat;

public class GDrainTest {

    private GDrain gDrain;
    private Drain drain;
    private BufferedImage canvas;
    private Graphics2D graphics;

    @BeforeEach
    public void setUp() {
        drain = new Drain();
        gDrain = new GDrain(drain, 50, 50);
        canvas = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
        graphics = canvas.createGraphics();
    }

    @Test
    public void testDraw() {

        gDrain.Draw(graphics);

        Color borderColor = new Color(canvas.getRGB(50, 50));
        assertThat(borderColor).isEqualTo(Color.BLACK);

    }


    @Test
    public void testIntersect() {
        assertThat(gDrain.Intersect(60, 60)).isTrue();
        assertThat(gDrain.Intersect(70, 70)).isTrue();
        assertThat(gDrain.Intersect(10, 10)).isFalse();
    }

    @Test
    public void testGetRef() {
        assertThat(gDrain.getRef()).isEqualTo(drain);
    }
}
