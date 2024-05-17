import java.awt.*;

public class GPump extends BaseShape{

    private Pump pump;
    /**
     * GPump osztály konstruktor, inicializálja a Pump objektumot és a koordinátákat.
     *
     * @param _pump a Pump objektum referencia
     */
    public GPump(Pump _pump){
        this.pump = _pump;
        x = 0;
        y = 0;
    }
    /**
     * GPump osztály konstruktor, inicializálja a Pump objektumot és a koordinátákat.
     *
     * @param _pump a Pump objektum referencia
     * @param _x    a Pump x koordinátája
     * @param _y    a Pump y koordinátája
     */
    public GPump(Pump _pump, int _x, int _y) {
        pump = _pump;
        x = _x;
        y = _y;
    }
    /**
     * Rajzolja ki a Pump objektumot a megadott grafikus objektumra.
     *
     * @param g a grafikus objektum, amire a Pump objektumot rajzoljuk
     */
    @Override
    public void Draw(Graphics g){
        g.setColor(Color.CYAN);
        g.fillRect(x,y,40,40);
        g.setColor(Color.BLACK);
        if (isSelected) g.setColor(Color.RED);
        g.drawRect(x,y,40,40);
        if (pump.GetSrc()!=null){
            int xdir=((GPipe)GameView.connections.get(Main.GetNameObs(pump.src))).getCenterx();
            int ydir=((GPipe)GameView.connections.get(Main.GetNameObs(pump.src))).getCentery();
            float xDirVec=(xdir-x);
            float yDirVec=ydir-y;

            float normWith=(float)Math.sqrt(Math.pow(xDirVec,2)+Math.pow(yDirVec,2));

            xDirVec=xDirVec/normWith;
            yDirVec=yDirVec/normWith;


            int pointToDrawx=x+10+(int)(xDirVec*50);
            int pointToDrawy=y+10+(int)(yDirVec*50);


            g.setColor(Color.green);
            g.fillOval(pointToDrawx+4,pointToDrawy+4,16,16);
            g.setColor(Color.BLACK);
        }
        if(pump.GetDst()!=null){
            int xdir=((GPipe)GameView.connections.get(Main.GetNameObs(pump.dst))).getCenterx();
            int ydir=((GPipe)GameView.connections.get(Main.GetNameObs(pump.dst))).getCentery();
            float xDirVec=(xdir-x);
            float yDirVec=ydir-y;

            float normWith=(float)Math.sqrt(Math.pow(xDirVec,2)+Math.pow(yDirVec,2));

            xDirVec=xDirVec/normWith;
            yDirVec=yDirVec/normWith;


            int pointToDrawx=x+10+(int)(xDirVec*50);
            int pointToDrawy=y+10+(int)(yDirVec*50);


            g.setColor(Color.RED);
            g.fillOval(pointToDrawx,pointToDrawy,16,16);
            g.setColor(Color.BLACK);

        }

        if (pump.IsBroken){
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(5));

            g.drawLine(x,y,x+40,y+40);
            g.drawLine(x+40,y,x,y+40);
            ((Graphics2D) g).setStroke(new BasicStroke(8));
        }

    }
    /**
     * Ellenőrzi, hogy a megadott koordináták metszik-e a Pump objektumot.
     *
     * @param target_x a cél x koordinátája
     * @param target_y a cél y koordinátája
     * @return true, ha a cél koordináták metszik a Pump objektumot, különben false
     */
    @Override
    public boolean Intersect(int target_x,int target_y){
        if (target_x>=x && target_x<=x+40 && target_y>=y && target_y<=y+40) return true;
        return false;
    }
    /**
     * Visszaadja a GPump objektumhoz tartozó Pump referenciáját.
     *
     * @return a Pump objektum referenciája
     */
    @Override
    public Object getRef(){
        return pump;
    }
}