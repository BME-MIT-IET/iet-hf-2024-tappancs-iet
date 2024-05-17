import java.awt.*;

public class GSource extends BaseShape{

    private Source source;
    private int radius=40;
    /**
     * Konstruktor egy GSource objektum létrehozásához, a megadott Source objektummal.
     *
     * @param _source a Source objektum, amelyhez a GSource objektum tartozik
     */
    public GSource(Source _source){
        this.source = _source;
        x = 0;
        y = 0;
    }
    /**
     * Konstruktor egy GSource objektum létrehozásához, a megadott Source objektummal és koordinátákkal.
     *
     * @param _source a Source objektum, amelyhez a GSource objektum tartozik
     * @param _x     az x koordináta értéke
     * @param _y     az y koordináta értéke
     */
    public GSource(Source _source, int _x, int _y) {
        source = _source;
        x = _x;
        y = _y;
    }
    /**
     * Kirajzolja a GSource objektumot a megadott grafikus objektumra.
     *
     * @param g a grafikus objektum, amire a GSource objektumot rajzolja
     */
    @Override
    public void Draw(Graphics g){
        g.setColor(Color.CYAN);

        g.fillOval(x,y,radius,radius);
        if (isSelected) g.setColor(Color.RED);
        g.drawOval(x,y,radius,radius);
    }
    /**
     * Ellenőrzi, hogy a GSource objektum átfedést (intersect) képez-e a megadott célpont koordinátáival.
     *
     * @param target_x a célpont x koordinátája
     * @param target_y a célpont y koordinátája
     * @return true, ha az objektum átfedést képez a célponttal, különben false
     */
    public boolean Intersect(int target_x,int target_y){
        if (Math.sqrt(Math.pow(target_x - (x+radius/2), 2) + Math.pow(target_y - (y+radius/2), 2)) < radius/2)
            return true;
        return false;
    }
    /**
     * Visszaadja a GSource objektumhoz tartozó Source objektum referenciáját.
     *
     * @return a GSource objektumhoz tartozó Source objektum referenciája
     */
    @Override
    public Object getRef(){
        return source;
    }
}