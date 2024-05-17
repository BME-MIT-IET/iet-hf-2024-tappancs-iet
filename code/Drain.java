//Kesz
public class Drain extends Field{
    int PipeCounter = 1;
    int TurnsUntilGenPipe = 3;
    private GameController gc;
    /**
     * A ciszternán tartózkodó játékos igényel
     * egy csövet a ciszternától, mely egyik vége
     * a létrehozó ciszternához van
     * csatlakoztatva. Amennyiben a ciszterna
     * nem tartalmaz generált csövet, nem
     * történik semmi. Visszatérési értéke egy
     * referencia a létrejött csőre.
     * @return visszaadja a generált pipe-t
     */
    public Drain(){
        gc=GameController.getInstance();
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("drain" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("drain" + i, this));
        Main.fields.put(this, this);
        Main.steppables.put(this, this);
    }
    @Override
    public Field RequestPipe(){
        if(PipeCounter>0){
            PipeCounter--;
            Pipe generated=new Pipe();
            ConnectPipe(generated);
            return generated;
        }
        return null;
    }
    public void SetPipeCounter(int c){
        this.PipeCounter = c;
    }
    /**
     * A ciszternán tartózkodó játékos igényel
     * egy pumpát a ciszternától. Visszatérési
     * értéke egy referencia a létrejött pumpára.
     * @return visszaadja a generált pump-t
     */
    @Override
    public Field RequestPump(){
        Pump generated=new Pump();
        return generated;
    }

    /**
     * A ciszterna step függvénye, mely minden
     * generált órajelnél meghívásra kerül a
     * Timer által. Mindegyik a ciszternához
     * kötött vízzel telt csőből eltünteti a vizet,
     * majd értesíti a GameController osztályt a
     * gyűjtött víz mennyiségének változásáról.
     */
    @Override
    public void Step(){
        for (Field f: Neighbors){
            if (f.PullWater()){
                gc.AddCollectedWater();
            }
        }
        if(TurnsUntilGenPipe == 0) {
            PipeCounter++;
            TurnsUntilGenPipe = 3;
        }
        else TurnsUntilGenPipe--;
    }
    public void Details(){
        super.Details();
        Main.WriteIntoFilesAndConsole("/t/tPipeCounter:"+ PipeCounter,false);

    }
}
