import java.awt.*;

public class GDrain extends BaseShape{
    private Drain drain;
    private int radius=40;
    /**
     * GDrain osztály konstruktora.
     * Létrehoz egy GDrain objektumot a megadott Drain objektum alapján.
     * Az objektum x és y koordinátáit inicializálja 0 értékkel.
     *
     * @param _drain a Drain objektum, amely alapján létrehozzuk a GDrain objektumot
     */
    public GDrain(Drain _drain){
        this.drain = _drain;
        x = 0;
        y = 0;
    }
    /**
     * GDrain osztály konstruktora.
     * Létrehoz egy GDrain objektumot a megadott Drain objektum, x és y koordináták alapján.
     *
     * @param _drain a Drain objektum, amely alapján létrehozzuk a GDrain objektumot
     * @param _x     az x koordináta értéke
     * @param _y     az y koordináta értéke
     */
    public GDrain(Drain _drain, int _x, int _y) {
        drain = _drain;
        x = _x;
        y = _y;
    }
    /**
     * Az objektum kirajzolásáért felelős Draw metódus.
     * A megadott Graphics objektum segítségével rajzolja ki a GDrain objektumot a megadott paraméterek alapján.
     *
     * @param g a Graphics objektum, amely segítségével rajzolni lehet
     */
    @Override
    public void Draw(Graphics g){

        g.setColor(Color.GRAY);
        g.fillOval(x,y,radius,radius);
        if (isSelected) g.setColor(Color.RED);
        g.drawOval(x,y,radius,radius);

    }
    /**
     * Ellenőrzi, hogy a GDrain objektum metszi-e a megadott célkoordinátákat.
     *
     * @param target_x a célkoordináta X értéke
     * @param target_y a célkoordináta Y értéke
     * @return true, ha a GDrain objektum metszi a célkoordinátákat, különben false
     */
    public boolean Intersect(int target_x,int target_y){
        if (Math.sqrt(Math.pow(target_x - (x+radius/2), 2) + Math.pow(target_y - (y+radius/2), 2)) < radius/2)
            return true;
        return false;
    }
    /**
     * Visszaadja a GDrain objektumhoz tartozó referencia objektumot.
     *
     * @return a GDrain objektumhoz tartozó referencia objektum
     */
    @Override
    public Object getRef(){
        return drain;
    }


}