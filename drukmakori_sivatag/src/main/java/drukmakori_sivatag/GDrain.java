package drukmakori_sivatag;

import java.awt.*;

public class GDrain extends BaseShape {
    private Drain drain;
    private int radius = 40;

    public GDrain(Drain _drain) {
        this.drain = _drain;
        x = 0;
        y = 0;
    }

    public GDrain(Drain _drain, int _x, int _y) {
        drain = _drain;
        x = _x;
        y = _y;
    }

    @Override
    public void Draw(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillOval(x, y, radius, radius);
        if (isSelected) {
            g.setColor(Color.RED);
        } else {
            g.setColor(Color.BLACK);
        }
        g.drawOval(x, y, radius, radius);
    }


    // kicsit megváltoztattam az Intersectet, mert nem kicsit off volt a matek sztem.
    public boolean Intersect(int target_x, int target_y) {
        int centerX = x + radius / 2;
        int centerY = y + radius / 2;
        int distance = (int) Math.sqrt(Math.pow(target_x - centerX, 2) + Math.pow(target_y - centerY, 2));
        return distance < radius / 2;
    }

    @Override
    public Object getRef() {
        return drain;
    }
}
