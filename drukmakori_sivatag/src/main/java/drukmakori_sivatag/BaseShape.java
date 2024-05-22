package drukmakori_sivatag;

import java.awt.*;

public abstract class BaseShape {
    protected int x;
    protected int y;

    protected int playersDrawn;
    protected boolean isVisible;

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }
    /**
     * Ellenőrzi, hogy az elem jelenleg kiválasztva van-e.
     *
     * @return true, ha az elem kiválasztva van, false egyébként
     */
    public boolean isSelected() {
        return isSelected;
    }
    /**
     * Beállítja az elem kiválasztott állapotát.
     *
     * @param selected true, ha az elemet kiválasztottá kell tenni, false egyébként
     */
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    protected boolean isSelected=false;
    public abstract boolean Intersect(int target_x, int target_y);
    public abstract void Draw(Graphics g);
    /**
     * Visszaadja az elem X koordinátáját.
     *
     * @return az elem X koordinátája
     */
    int GetX(){
        return x;
    }
    /**
     * Visszaadja az elem Y koordinátáját.
     *
     * @return az elem Y koordinátája
     */
    int GetY(){
        return y;
    }

    public Object getRef(){
        return null;
    }

}
