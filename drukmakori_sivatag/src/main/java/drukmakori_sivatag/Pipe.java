package drukmakori_sivatag;

import java.util.Random;

public class Pipe extends Field{
    boolean Occupied = false;


    boolean HasWater = false;
    boolean Slippery = false;
    boolean Sticky = false;
    int DebuffTimer = 0;

    /**
     * Getter for testing purposes only
     */

    public boolean isHasWater() {
        return HasWater;
    }

    int TicksUntilBreakable = 0;

    /**
     * Getter/Setter for testing purposes only
     */

    public int getTicksUntilBreakable() {
        return TicksUntilBreakable;
    }

    public void setTicksUntilBreakable(int ticksUntilBreakable) {
        TicksUntilBreakable = ticksUntilBreakable;
    }
    /**
     * Getter for testing purposes only
     */

    public GameController getGc() {
        return gc;
    }

    GameController gc;
    public Pipe(){
        gc=GameController.getInstance();
        TicksUntilBreakable = 0;
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("pipe" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("pipe" + i, this));
        Main.fields.put(this, this);
        Main.steppables.put(this, this);
    }


    /**
     * A cső step függvénye.
     * A Timer minden órajelen meghívja.
     * Amennyiben a cső lyukas, és tartalmaz vizet, a víz kifolyik a csőből.
     */
    @Override
    public void Step(){
        if (HasWater && IsBroken){
            HasWater=false;
            gc.AddLeakedWater();
        }
        if (DebuffTimer==0){
            Sticky=false;
            Slippery=false;
        }
        if(TicksUntilBreakable > 0)
            TicksUntilBreakable--;
        if(DebuffTimer > 0)
            DebuffTimer--;
    }
    @Override
    public void Fix(){
        IsBroken=false;
        TicksUntilBreakable=new Random().nextInt(10);
    }
    /**
     * A függvény nem csinál semmit, mivel nem lehet csőre csövet kötni.
     *
     * @param f
     * @return
     */
    @Override
    public boolean ConnectPipe(Field f){
        return false;
    }

    /**
     * A függvény nem csinál semmit, mivel nem lehet csőre csövet kötni.
     *
     * @param f
     * @return
     */
    @Override
    public boolean DisconnectPipe(Field f){
        return false;
    }

    /**
     * Visszatérési értéke True, ha léphet játékos a csőre, False különben.
     * A visszatérési érték eldöntése az Occupied alapján történik.
     * A függvény az Occupied értékét True-ra módosítja, ha befogad egy játékost.
     *
     * @param p
     * @return
     */
    @Override
    public boolean Accept(Player p){
        if (Occupied){
            return false;
        }
        if(Slippery){
            if(Neighbors.size() < 2) {
                p.MoveTo(Neighbors.get(0));
            } else {
                if(Main.isRandomEnabled) {
                    p.MoveTo(Neighbors.get(new Random().nextInt(0,2)));
                } else {
                    p.MoveTo(Neighbors.get(0));
                }
            }
            return false;
        }
        Occupied=true;
        return true;
    }

    /**
     * A cső beállítja az Occupied értékét False-ra.
     *
     * @param p
     * @return
     */
    @Override
    public boolean Remove(Player p){
        Occupied=false;
        return true;
    }

    /**
     * A cső kilyukad. (Az IsBroken értéke True-ra változik.)
     */
    @Override
    public void Sabotage(){
        if (TicksUntilBreakable==0){
            IsBroken=true;
        }
    }

    /**
     * Ha van víz a csőben igazad ad vissza és átállítja HasWater értékét false-ra.
     * Ellenkező esetben false a visszatérési érték.
     * @return
     */
    @Override
    public boolean PullWater(){
        if (IsBroken && HasWater){
            gc.AddLeakedWater();
            HasWater=false;
            return false;
        }
        else if (HasWater){
            HasWater=false;
            return true;
        }
        return false;
    }

    /**
     * Kiüríti a csőből a vizet.
     */
    @Override
    public void RemoveWater(){
        HasWater=false;
    }

    /**
     * Megtölti a csövet vízzel.
     * @return
     */
    @Override
    public boolean Fill(){
        if (IsBroken){
            gc.AddLeakedWater();
            return true;
        }
        else if(Neighbors.size() == 1){
            gc.AddLeakedWater();
            return true;
        }
        else if(!HasWater){
            SetHasWater(true);
            return true;
        }
        return false;
    }

    /**
     * A pipe megkapja a pumpát, amit a játékos birtokol.
     * A jelenlegi egyik végéről lecsatlakozik, majd rácsatlakozik a kapott pumpára.
     * Létrehoz egy új csövet, amelyet rácsatol a saját előző végpontjára, amelyről lecsatlakozott, valamint a kapott pumpára.
     *
     * @param f
     * @return
     */
    // A játszók elkerítve, mint annó a lágerek. A romáktól hallott mondatok lettek mára slágerek.
    public boolean PlacePump(Pump f){
        //elmentjük a túloldalt, mert a kövi sorban elvesztenénk a referenciát.
        Field otherSide = GetNeighbors().get(1);
        //lekötjük a csövet a túloldali pumpáról.
        otherSide.DisconnectPipe(this);
        //bekötjük a lerakandó pumpába.
        f.ConnectPipe(this);
        //létrehozzuk a vágott csövet.
        Pipe cut = new Pipe();
        //belekötjük a lerakandóba a vágott csövet.
        Pump otherPump=(Pump)otherSide;

        if (otherPump.GetDst()==this) otherPump.SetDst(cut);
        else if (otherPump.GetSrc()==this) otherPump.SetSrc(cut);

        f.ConnectPipe(cut);
        f.SetSrc(cut);
        f.SetDst(this);
        //belekötjük a túloldalba is a vágott csövet.
        otherSide.ConnectPipe(cut);
        //beállítjuk a csöveknek is a szomszédságokat
        cut.AddNeighbour(f);
        cut.AddNeighbour(otherSide);
        Neighbors.remove(otherSide);
        AddNeighbour(f);
        //jelzünk igazzal a mechanicnak, hogy sikerült a story. Neki el kell engednie a pumpát!
        return true;
    }

    /**
     * Visszatér az Occupied változó értékével.
     *
     * @return
     */
    @Override
    public boolean GetOccupied(){
        return Occupied;
    }

    /**
     * Beállítja a HasWater változó értékét.
     *
     * @param a
     */
    public void SetHasWater(boolean a){
        HasWater=a;
    }
    public void MakeSlippery(){
        Slippery=true;
        DebuffTimer=5;
    }
    public void MakeSticky(){
        Sticky=true;
        DebuffTimer=5;
    }
    public boolean IsSticky(){
        return Sticky;
    }
    public boolean GetSlippery(){
        return Slippery;
    }
    public void SetSlippery(boolean val){
        Slippery=val;
    }
    public void SetSticky(boolean val){
        Sticky=val;
    }
    public int GetDebuffTimer(){
        return DebuffTimer;
    }
    public void SetDebuffTimer(int val){
        DebuffTimer=val;
    }
    public void Details(){
        super.Details();
        String indent="\t\t";
        Main.WriteIntoFilesAndConsole(indent+"Occupied: "+Occupied,false);
        Main.WriteIntoFilesAndConsole(indent+"HasWater: "+HasWater,false);
        Main.WriteIntoFilesAndConsole(indent+"Slippery: "+Slippery,false);
        Main.WriteIntoFilesAndConsole(indent+"Sticky: "+Sticky,false);
        Main.WriteIntoFilesAndConsole(indent+"DebuffTimer: "+DebuffTimer,false);
        Main.WriteIntoFilesAndConsole(indent+"TicksUntilBreakable: "+TicksUntilBreakable,false);


    }
}