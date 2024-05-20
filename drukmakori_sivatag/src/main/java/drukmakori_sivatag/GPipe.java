package drukmakori_sivatag;

import java.awt.*;
/**
 * A GPipe osztály reprezentálja a Pipe objektum grafikus megjelenítését és viselkedését.
 */
public class GPipe extends BaseShape{

    private Pipe pipe;
    private Color water=new Color(32,178,170,150);
    private Color pipe_grey=new Color(40,40,40,80);
    /**
     * Visszaadja a Pipe objektum középpontjának x koordinátáját.
     *
     * @return a középpont x koordinátája
     */
    public int getCenterx() {
        return centerx;
    }
    /**
     * Visszaadja a Pipe objektum középpontjának y koordinátáját.
     *
     * @return a középpont y koordinátája
     */

    public void setCenterx(int centerx) {
        this.centerx = centerx;
    }

    public int getCentery() {
        return centery;
    }

    public void setCentery(int centery){
        this.centery = centery;
    }


    private int centerx,centery;
    /**
     * GPipe osztály konstruktor, inicializálja a Pipe objektumot és a koordinátákat.
     *
     * @param _pipe a Pipe objektum referencia
     */
    public GPipe(Pipe _pipe){
        this.pipe = _pipe;
        x = 0;
        y = 0;

    }
    /**
     * GPipe osztály konstruktor, inicializálja a Pipe objektumot és a koordinátákat.
     *
     * @param _pipe a Pipe objektum referencia
     * @param _x    a Pipe x koordinátája
     * @param _y    a Pipe y koordinátája
     */
    public GPipe(Pipe _pipe, int _x, int _y) {
        pipe = _pipe;
        x = _x;
        y = _y;
    }
    /**
     * Rajzolja ki a Pipe objektumot a megadott grafikus objektumra.
     *
     * @param g a grafikus objektum, amire a Pipe objektumot rajzoljuk
     */
    @Override
    public void Draw(Graphics g){


        if (pipe.HasWater) g.setColor(water);
        else g.setColor(pipe_grey);

        ((Graphics2D) g).setStroke(new BasicStroke(8));


        if (pipe.GetNeighbors().size()<=0) return;
        BaseShape tmp=GameView.connections.get(Main.GetNameObs(pipe.GetNeighbors().get(0)));
        if (pipe.GetNeighbors().size()==1){
            g.drawLine(tmp.GetX()+20,tmp.GetY()+20,tmp.GetX()+20+20, tmp.GetY()+40);
            centerx=tmp.GetX()+20;
            centery=tmp.GetY()+20;
            if (isSelected) g.setColor(Color.RED);
            else g.setColor(Color.BLACK);
            g.fillOval(centerx+15,centery+15,30,30);
            if (pipe.IsBroken){
                g.setColor(Color.RED);
                ((Graphics2D) g).setStroke(new BasicStroke(5));

                g.drawLine(centerx+10+10,centery+10+10,centerx+28+10,centery+28+10);
                g.drawLine(centerx+28+10,centery+10+10,centerx+10+10,centery+28+10);
                ((Graphics2D) g).setStroke(new BasicStroke(8));
            }
            return;
        }
        BaseShape tmp2=GameView.connections.get(Main.GetNameObs(pipe.GetNeighbors().get(1)));
        g.drawLine(tmp.GetX()+20,tmp.GetY()+20,tmp2.GetX()+20, tmp2.GetY()+20);

        centerx=(tmp.GetX()+tmp2.GetX())/2;
        centery=(tmp.GetY()+tmp2.GetY())/2;
        if (isSelected) g.setColor(Color.RED);
        else if (pipe.IsSticky()) g.setColor(Color.GREEN);
        else if (pipe.IsSlippery()) g.setColor(Color.YELLOW);


        g.fillOval(centerx+5,centery+5,30,30);
        if (pipe.IsBroken){
            g.setColor(Color.RED);
            ((Graphics2D) g).setStroke(new BasicStroke(5));

            g.drawLine(centerx+10,centery+10,centerx+28,centery+28);
            g.drawLine(centerx+28,centery+10,centerx+10,centery+28);
            ((Graphics2D) g).setStroke(new BasicStroke(8));

        }


        g.setColor(Color.BLACK);

    }
    /**
     * Ellenőrzi, hogy a megadott koordináták metszik-e a Pipe objektumot.
     *
     * @param target_x a cél x koordinátája
     * @param target_y a cél y koordinátája
     * @return true, ha a cél koordináták metszik a Pipe objektumot, különben false
     */
    @Override
    public boolean Intersect(int target_x,int target_y){

        if (Math.sqrt(Math.pow(target_x - (centerx+20), 2) + Math.pow(target_y - (centery+20), 2)) < 15){
            return true;
        }

        return false;
    }
    /**
     * Visszaadja a GPipe objektumhoz tartozó Pipe referenciáját.
     *
     * @return a Pipe objektum referenciája
     */
    @Override
    public Object getRef(){
        return pipe;
    }
}
