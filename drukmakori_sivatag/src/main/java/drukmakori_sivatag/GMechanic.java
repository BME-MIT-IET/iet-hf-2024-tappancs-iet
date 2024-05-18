package drukmakori_sivatag;

import java.awt.*;

public class GMechanic extends BaseShape{

    private Mechanic mechanic;

    /**
     * GMechanic konstruktora.
     * Létrehoz egy GMechnic objektumot a Mechanichoz.
     * @param _mechanic
     */
    public GMechanic(Mechanic _mechanic){
        this.mechanic = _mechanic;
        x = 0;
        y = 0;
    }

    /**
     * GMechanic konstruktora.
     * @param _mechanic Mechanic melyet reprezentál a GMechanic.
     * @param _x X koordinátája a pályán
     * @param _y Y koordinátája a pályán
     */
    public GMechanic(Mechanic _mechanic, int _x, int _y) {
        mechanic = _mechanic;
        x = _x;
        y = _y;
    }
    int drawnScale=1;

    /**
     Kirajzolja az objektumot a grafikus felületre.
     A metódus a megadott grafikus kontextusban kirajzolja az objektumot. Először meghatározza az objektum
     pozícióját az x és y koordináták beállításával. Ezután kiszámítja a csúcspontok x- és y-koordinátáit,
     majd ezek alapján kirajzolja a sokszöget a grafikus felületen. Ha az objektum ki van jelölve, a színét
     pirosra állítja. Végül növeli a játékosok számlálóját és beállítja a skálázási értéket.
     @param g a grafikus kontextus, amire az objektumot kirajzolja
     */
    @Override
    public void Draw(Graphics g){
        if (Main.GetNameObs(mechanic.GetPosition()).contains("pipe")){
            x=((GPipe)GameView.connections.get(Main.GetNameObs(mechanic.GetPosition()))).getCenterx();
            y=((GPipe)GameView.connections.get(Main.GetNameObs(mechanic.GetPosition()))).getCentery();
        }
        else {
            x=GameView.connections.get(Main.GetNameObs(mechanic.GetPosition())).GetX();
            y=GameView.connections.get(Main.GetNameObs(mechanic.GetPosition())).GetY();
        }
        y-=10;

        if(this.mechanic == Main.GetAsMechanic(Main.selectedPlayer)){
            g.setColor(Color.RED);
        }
        else{
            g.setColor(Color.PINK);
        }
        int scale=GameView.connections.get(Main.GetNameObs(mechanic.GetPosition())).playersDrawn;
        int[] xPoints = {x+24*((scale%2)-1),x+5+24*((scale%2)-1), x+10+24*((scale%2)-1)};  // x-coordinates of the vertices
        int scaley=(scale-1)/2;
        int h=(int)Math.sqrt(10*10 - 5*5);
        int[] yPoints = {y-scaley*3*h-5, y-h*3*scaley-h-5, y-scaley*3*h-5};
        if (isSelected) g.setColor(Color.RED);
        g.drawPolygon(xPoints,yPoints,3);
        GameView.connections.get(Main.GetNameObs(mechanic.GetPosition())).playersDrawn++;
        drawnScale=scale;
    }

    /**
     * Metszet ellenőrzés.
     * Adott GMechanicban bent van e kattintott pont.
     * @param target_x  Kattintott x koordináta.
     * @param target_y  Kattintott y koordináta.
     * @return Bool érték attól függően, hogy az adott pont a objektomon belü van-e.
     */
    public boolean Intersect(int target_x,int target_y){
        int[] xPoints = {x+24*((drawnScale%2)-1),x+5+24*((drawnScale%2)-1), x+10+24*((drawnScale%2)-1)};
        int scaley=(drawnScale-1)/2;
        int h=(int)Math.sqrt(10*10 - 5*5);
        int[] yPoints = {y-scaley*3*h-5, y-h*3*scaley-h-5, y-scaley*3*h-5};

        if (target_x>=xPoints[0] && target_x<=xPoints[0]+xPoints[2]-xPoints[1] && target_y>=yPoints[0]-(yPoints[0]-yPoints[1]) && target_y<=yPoints[0]) return true;
        return false;
    }

    /**
     * Visszaadja a referencia mehanicot.
     * @return referencia mechanic.
     */
    @Override
    public Object getRef(){
        return mechanic;
    }
}