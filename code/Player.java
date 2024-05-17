public abstract class Player {

    Field Position;
    boolean Stuck = false;

    /**
     *A játékos megkísérel átlépni egy
     * szomszédos elemre.
     * @param f az elem melyre mozdulunk
     */
    public void MoveTo(Field f){
        if(!Position.IsSticky()) SetStuck(false);
        if(!Stuck){
            if(f.Accept(this)) {
                Position.Remove(this);
                SetPosition(f);
            }
        }
        if(Position.IsSticky()) SetStuck(true);
    }

    /**
     *A játékos átállítja az általa elfoglalt pumpa
     * be- és kimenetét.
     * @param src a bemeneti elem
     * @param dst a kimeneteli elem
     */
    public void ChangePump(Field src,Field dst){
        Position.Change(src, dst);
    }

    /**
     *Visszaadja a helyzetét.
     * @return a helyzete
     */
    public Field GetPosition(){
        return Position;
    }

    /**
     *Beállítja a helyzetét.
     * @param position az elem mely lesz a helyzete
     */
    public void SetPosition(Field position){
        Position = position;
    }
    public void SetStuck(boolean stuck){
        Stuck = stuck;
    }
    public void Sabotage(){
        Position.Sabotage();
    }

    public void MakeSticky(){
        Position.MakeSticky();
    }

    public void Details(){
        String indent="\t\t";
        Main.WriteIntoFilesAndConsole("\tAttributes:",false);
        Main.WriteIntoFilesAndConsole(indent+"Position: "+Main.GetNameObs(Position),false);
        Main.WriteIntoFilesAndConsole(indent+"Stuck: "+Stuck,false);
    }
}