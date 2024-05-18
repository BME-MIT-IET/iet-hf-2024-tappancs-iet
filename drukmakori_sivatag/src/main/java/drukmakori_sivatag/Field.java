package drukmakori_sivatag;

import java.util.ArrayList;
public abstract class Field implements Steppable {
    protected ArrayList<Field> Neighbors = new ArrayList<>();
    protected boolean IsBroken=false;
    /**
     * Igazat ad vissza ,ha a paraméterként megadott Field szomszédos azzal a Fieldel amelyen a függvényt meghívtuk.
     * Ha nem szomszédosak hamisat ad vissza.
     *
     * @param f
     * @return
     */
    public boolean IsNeighbor(Field f){
        for (Field tmp: Neighbors) {
            if (tmp==f) return true;
        }
        return false;
    }

    /**
     *Megjavítja az adott field-et, tehát az IsBroken attribútum értékét igazra állítja.
     */
    public void Fix(){
        IsBroken=false;
    }

    /**
     *Nem csinál semmit , leszármazott definiálja felül.
     */
    public void Sabotage(){
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @param src forrás
     * @param dst cél
     */
    public void Change(Field src, Field dst){
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @return
     */
    public Field RequestPipe(){
        return null;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @return
     */
    public Field RequestPump(){
        return null;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     */
    public void Step(){
    }

    /**
     * Az adott objektum a paraméterként átadott csővel szomszédsági viszonyt alakít ki. (Csatlakoznak.)
     *
     * @param f
     * @return
     */
    public boolean ConnectPipe(Field f){
        if(IsNeighbor(f)) return false;
        f.AddNeighbour(this);
        Neighbors.add(f);
        return true;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @param f
     * @return
     */
    public boolean PlacePump(Pump f){
        return false;
    }

    /**
     * Visszaadja ,hogy a játékos ráléphet-e az adott mezőre.
     *
     * @param p
     * @return
     */
    public boolean Accept(Player p){
        return true;
    }

    /**
     * Alapértelmezés szerint nem csinál semmit, a pipe osztály felüldefiniálja.
     *
     * @param p
     * @return
     */
    public boolean Remove(Player p){
        return true;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     */
    public void RemoveWater(){
    }
    public boolean ContainsWater(){
        return false;
    }
    /**
     * Felvesz egy Field elemet az adott elem szomszédai közé. Hozzáadja a Neighbors attribútumhoz.
     *
     * @param f
     */
    public void AddNeighbour(Field f){
        if(IsNeighbor(f)) return;
        Neighbors.add(f);
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @return
     */
    public boolean Fill(){
        return false;
    }

    /**
     * Az f paraméterben átadott csövet lecsatlakoztatja az objektumról.
     *
     * @param f
     * @return
     */
    public boolean DisconnectPipe(Field f){
        if(IsNeighbor(f)) {
            Neighbors.remove(f);
            return true;
        }
        return false;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @return
     */
    public boolean PullWater(){
        return false;
    }

    /**
     * Beállítja az IsBroken értékét.
     *
     * @param broken
     */
    public void SetIsBroken(boolean broken) {
        IsBroken=broken;
    }

    /**
     * Nem csinál semmit, leszármazott definiálja felül.
     *
     * @return
     */
    public boolean GetOccupied(){
        return false;
    }

    /**
     * Visszaadja a Neighbors értékét.
     *
     * @return eighbors értékét.
     */
    public ArrayList<Field> GetNeighbors(){
        return Neighbors;
    }

    /**
     * Beállítja a Neighbors értékét.
     *
     * @param neighbors
     */
    public void SetNeighbors(ArrayList<Field> neighbors){
        Neighbors = neighbors;
    }

    public boolean IsSticky(){
        return false;
    }

    public void MakeSticky() {

    }
    public boolean IsSlippery(){
        return false;
    }
    public void MakeSlippery() {
    }
    public void Details(){
        Main.WriteIntoFilesAndConsole("\tNeighbours:",false);
        for (var n :Neighbors){
            Main.WriteIntoFilesAndConsole("\t\t"+Main.GetNameObs(n),false);
        }
        Main.WriteIntoFilesAndConsole("Attributes:",false);
        Main.WriteIntoFilesAndConsole("\tIsBroken:" + (IsBroken? "true" :"false"),false);

    }
}