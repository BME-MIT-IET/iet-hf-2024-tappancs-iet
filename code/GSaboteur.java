import java.awt.*;

public class GSaboteur extends BaseShape{

    private Saboteur saboteur;
    /**
     * Konstruktor egy GSaboteur objektum létrehozásához, a megadott Saboteur objektummal és alapértelmezett koordinátákkal.
     *
     * @param _saboteur a Saboteur objektum, amelyhez a GSaboteur objektum tartozik
     */
    public GSaboteur(Saboteur _saboteur){
        this.saboteur = _saboteur;
        x = 0;
        y = 0;
    }
    /**
     * Konstruktor egy GSaboteur objektum létrehozásához, a megadott Saboteur objektummal és koordinátákkal.
     *
     * @param _saboteur a Saboteur objektum, amelyhez a GSaboteur objektum tartozik
     * @param _x        az x koordináta értéke
     * @param _y        az y koordináta értéke
     */
    public GSaboteur(Saboteur _saboteur, int _x, int _y) {
        saboteur = _saboteur;
        x = _x;
        y = _y;
    }
    int drawnScale=1;
    /**
     * Kirajzolja a GSaboteur objektumot a megadott grafikus objektumra.
     *
     * @param g a grafikus objektum, amire a GSaboteur objektumot rajzolja
     */
    @Override
    public void Draw(Graphics g){
        g.setColor(Color.BLACK);
        if (Main.GetNameObs(saboteur.GetPosition()).contains("pipe")){
            x=((GPipe)GameView.connections.get(Main.GetNameObs(saboteur.GetPosition()))).getCenterx();
            y=((GPipe)GameView.connections.get(Main.GetNameObs(saboteur.GetPosition()))).getCentery();
        }
        else {
            x=GameView.connections.get(Main.GetNameObs(saboteur.GetPosition())).GetX();
            y=GameView.connections.get(Main.GetNameObs(saboteur.GetPosition())).GetY();
        }
        y-=10;

        if(this.saboteur == Main.GetAsSaboteur(Main.selectedPlayer)){
            g.setColor(Color.BLACK);
        }
        else{
            g.setColor(Color.LIGHT_GRAY);
        }
        int scale=GameView.connections.get(Main.GetNameObs(saboteur.GetPosition())).playersDrawn;
        int[] xPoints = {x+24*(scale%2-1),x+5+24*(scale%2-1), x+10+24*(scale%2-1)};  // x-coordinates of the vertices
        int scaley=(scale-1)/2;
        int h=(int)Math.sqrt(10*10 - 5*5);
        int[] yPoints = {y-scaley*3*h-5, y-h*3*scaley-h-5, y-scaley*3*h-5};
        if (isSelected) g.setColor(Color.RED);
        g.drawPolygon(xPoints,yPoints,3);
        GameView.connections.get(Main.GetNameObs(saboteur.GetPosition())).playersDrawn++;
        drawnScale=scale;
    }
    /**
     * Ellenőrzi, hogy a GSaboteur objektum átfedést (intersect) képez-e a megadott célpont koordinátáival.
     *
     * @param target_x a célpont x koordinátája
     * @param target_y a célpont y koordinátája
     * @return true, ha az objektum átfedést képez a célponttal, különben false
     */
    public boolean Intersect(int target_x,int target_y){
        int[] xPoints = {x+24*(drawnScale%2-1),x+5+24*(drawnScale%2-1), x+10+24*(drawnScale%2-1)};  // x-coordinates of the vertices
        int scaley=(drawnScale-1)/2;
        int h=(int)Math.sqrt(10*10 - 5*5);
        int[] yPoints = {y-scaley*h-5, y-h*scaley-h-5, y-scaley*h-5};


        if (target_x>=xPoints[0] && target_x<=xPoints[0]+xPoints[2]-xPoints[1] && target_y>=yPoints[0]-(yPoints[0]-yPoints[1]) && target_y<=yPoints[0]) return true;
        return false;
    }
    /**
     * Visszaadja a GSaboteur objektumhoz tartozó Saboteur referenciáját.
     *
     * @return a Saboteur objektum referenciája
     */
    @Override
    public Object getRef(){
        return saboteur;
    }
}