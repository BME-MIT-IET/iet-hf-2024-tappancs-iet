package drukmakori_sivatag;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Timer {
    private static Timer instance;
    static ArrayList<Steppable> steppables=new ArrayList<Steppable>();

    /**
     * @return
     */
    public static Timer getInstance() {
        if(instance == null) {
            instance = new Timer();
        }
        System.out.println("Timer.getInstance()");
        return instance;
    }
    private Timer(){

        //System.out.println("\t-Timer created-");
    }

    /**
     * A függvény meghívódik megadott időközönként.
     * Ezen függvényen belül a Timer meghívja az összes steppable listában lévő objektum Step függvényét.
     */
    public static void Tick(){
        for (var tmp:steppables ){
            tmp.Step();
            
        }

    }

    /**
     * @return Visszatér a steppable lista tartalmával.
     */
    public static List<Steppable> GetSteppable(){
        return steppables;
    }

    /**
     * Beállítja a steppable lista tartalmát.
     * @param input
     */
    public static void SetSteppable(ArrayList<Steppable> input){
        steppables=input;
    }
}
