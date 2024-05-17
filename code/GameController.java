public class GameController implements Steppable{
    /**
     * Beállítja a játék időtartamát.
     * @param gameTime A játék időtartama.
     */
    public void setGameTime(int gameTime) {
        GameTime = gameTime;
    }
    /**
     * Beállítja a maximális játék időtartamát.
     * @param maxGameTime A maximális játék időtartama.
     */
    public void setMaxGameTime(int maxGameTime) {
        MaxGameTime = maxGameTime;
    }
    /**
     * Beállítja a szivárgott víz mennyiségét.
     * @param leakedWater A szivárgott víz mennyisége.
     */
    public void setLeakedWater(int leakedWater) {
        LeakedWater = leakedWater;
    }
    /**
     * Beállítja a begyűjtött víz mennyiségét.
     * @param collectedWater A begyűjtött víz mennyisége.
     */
    public void setCollectedWater(int collectedWater) {
        CollectedWater = collectedWater;
    }

    /**
     * Visszaadja a játék időtartamát.
     * @return A játék időtartama.
     */
    public int getGameTime() {
        return GameTime;
    }
    /**
     * Visszaadja a szivárgott víz mennyiségét.
     * @return A szivárgott víz mennyisége.
     */
    public int getLeakedWater() {
        return LeakedWater;
    }
    /**
     * Visszaadja a begyűjtött víz mennyiségét.
     * @return A begyűjtött víz mennyisége.
     */
    public int getCollectedWater() {
        return CollectedWater;
    }
    private int GameTime = 0;
    private int MaxGameTime = 10;
    private int LeakedWater = 0;
    private int CollectedWater = 0;
    private boolean GameEndedInterrupt = false;
    private static GameController instance;


    /**
     * A játék részleteinek kiírása  a konzolra.
     * Kiírja a játék attribútumait, mint például a GameTime, MaxGameTime, LeakedWater és CollectedWater értékeket.
     */
    public void Details(){
        String indent="\t\t";
        Main.WriteIntoFilesAndConsole("Attributes:",false);
        Main.WriteIntoFilesAndConsole(indent+"GameTime: "+GameTime,false);
        Main.WriteIntoFilesAndConsole(indent+"MaxGameTime: "+MaxGameTime,false);
        Main.WriteIntoFilesAndConsole(indent+"LeakedWater: "+LeakedWater,false);
        Main.WriteIntoFilesAndConsole(indent+"CollectedWater: "+ CollectedWater,false);

    }
    /**
     * Létrehoz az objektumot.
     */
    private GameController() {
        Main.steppables.put(this, this);
    }

    /**
     * Visszaadja a gamekontrollert.
     * @return az aktuális gamekontrollert
     */
    public static GameController getInstance() {
        if(instance == null) {
            instance = new GameController();
        }

        return instance;
    }
    /**
     * Megsemmisíti az aktuális példányt.
     * Az aktuális példány null értéket kap.
     */
    public static void Destroy(){
        instance = null;
    }
    /**
     * Új játékot indít a megadott maximális játék idővel.
     * @param MaxGTime A maximális játék idő.
     */
    public void NewGame(int MaxGTime){

        MaxGameTime=MaxGTime;
        //Make basic map
        Source src=new Source();
        Pipe pipe1=new Pipe();
        Pipe pipe2=new Pipe();
        Pump pump1=new Pump();
        Drain drain1=new Drain();
        Mechanic mech1=new Mechanic();
        Saboteur sabo1=new Saboteur();

        mech1.SetPosition(src);
        sabo1.SetPosition(pipe2);

        src.ConnectPipe(pipe1);
        pump1.ConnectPipe(pipe1);
        pump1.ConnectPipe(pipe2);
        pipe2.ConnectPipe(drain1);
    }

    /**
     * A GameController step függvénye, mely
     * minden generált órajelnél meghívásra
     * kerül a Timer által. A GameController
     * növeli a GameTime attribútum értékét,
     * majd ha az elérte a MaxGameTime értékét,
     * befejezi a játékot.
     */
    public void Step(){
        IncreaseElapsedTime();
        if (GameTime==MaxGameTime)
            EndGame();
    }
    public boolean GetEndGameInt(){
        return GameEndedInterrupt;
    }
    /**
     * Eggyel növeli a kifolyt víz mennyiségét(LeakedWater).
     */
    public void AddLeakedWater(){
        LeakedWater++;
    }

    /**
     * Eggyel növeli a ciszternákba folyt víz
     * mennyiségét (CollectedWater).
     */
    public void AddCollectedWater(){
        CollectedWater++;
    }

    /**
     * A játék befejeződik, maj a nyertes
     * kihirdetésre kerül.
     */
    public void EndGame(){
        GameEndedInterrupt = true;
    }

    /**
     * A GameTime növekszik eggyel.
     */
    public void IncreaseElapsedTime(){
        GameTime++;
    }
}