package drukmakori_sivatag;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;


import static java.lang.String.*;

public class Functions {
    private static final String NO_PLAYER = "No player selected";
    private static final String STICKY = "Sticky";
    private static final String SLIPPERY = "Slippery";
    

    /**
     * A jelenleg kibválasztott játékos kér egy kiválasztott típusú komponenst
     * az általa elfoglalt Field-től.
     * Pumpát csak akkor kaphat a játékos, ha Drain-en áll.
     * Csövet csak akkor kaphat a játékos, ha Drain-en áll,
     * illetve az elfoglalt Drain tartalmaz generált csövet.
     * Csak akkor kap komponenst a játékos, ha típusa Mechanic.
     * * Amennyiben a lépés sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a lépés nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: Az igényelt komponens típusa
     */
    public static void RequestPart(String[] args){
        boolean success = false;

        if(!argCheck(args, 2, "Not enough arguments for RequestPart", "Too many arguments for RequestPart")) return;

        Mechanic mech = Main.GetAsMechanic(Main.selectedPlayer);
        String player = Main.GetNameObs(mech);

        if(mech == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER + ", or player selected is not a Mechanic",false);
            return;
        }
        String part_type = args[1];

        if(!part_type.equals("Pipe") && !part_type.equals("Pump")){
            Main.WriteIntoFilesAndConsole("Selected object \"" + args[1] + "\" is not a valid object",false);
            return;
        }

        String part;
        if(part_type.equals("Pipe")){
            success = mech.PickUpPipe();
            part = Main.GetNameObs(mech.GetControlledPipe());
        }
        else {
            success = mech.PickUpPump();
            part = Main.GetNameObs(mech.GetControlledPump());
        }


        if(success) Main.WriteIntoFilesAndConsole(String.format("%s received new %s %s", player, part_type,part),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn't receive %s", player, part_type),false);
    }
    /**
     * A jelenleg kiválasztott játékos megísérel
     * átlépni az argumentumként átadott mezőre.
     * Amennyiben a lépés sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a lépés nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Ha a lépés céljaként kitűzött cső slippery, a végpozíció
     * kerül kiírásra.
     * @param args A bemeneti argumentumok
     *             args[1]: Megtenni kívánt lépés célmezője
     */
    public static void Move(String[] args){
        boolean success = false;
        if(!argCheck(args, 2, "Not enough arguments for Move", "Too many arguments for Move")) return;

        Player player = Main.GetAsMechanic(Main.selectedPlayer);

        if (player==null){
            player=Main.GetAsSaboteur(Main.selectedPlayer);
        }
        String playerName = Main.selectedPlayer;




        if(player == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        String destination = args[1];
        if(Main.GetAsField(destination) == null) {
            Main.WriteIntoFilesAndConsole("The destination of Move is not a Field",false);
            return;
        }
        Field start = player.Position;

        if(!start.IsNeighbor(Main.GetAsField(destination))){
            Main.WriteIntoFilesAndConsole("The destination of Move is not a Neighbor of Position",false);
            return;
        }
        player.MoveTo(Main.GetAsField(destination));

        if(player.GetPosition() != start)Main.WriteIntoFilesAndConsole(String.format("%s successfully moved to %s", playerName, Main.GetNameObs(player.Position)),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn’t move to %s", playerName, destination),false);

    }
    /**
     * A jelenleg kiválasztott játékos megkísérli az általa
     * elfoglalt mező elrontását.
     * A parancs abban az esetben sikeres, ha a játékos
     * által elfoglalt Field típusa cső.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincsenek bemeneti argumentumok.
     */
    public static void Sabotage(String[] args){
        boolean success = false;
        if(args.length != 1){
            Main.WriteIntoFilesAndConsole("Incorrect number of arguments for Sabotage",false);
            return;
        }

        Player player = Main.GetAsMechanic(Main.selectedPlayer);
        if (player==null){
            player=Main.GetAsSaboteur(Main.selectedPlayer);
        }
        String playerName = Main.selectedPlayer;

        if(player == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        player.Sabotage();
        success=player.GetPosition().IsBroken;

        if(success) Main.WriteIntoFilesAndConsole(String.format("%s sabotaged %s", playerName,Main.GetNameObs(player.GetPosition())),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn’t sabotage", playerName),false);

    }
    /**
     * A játékos megkísérel lehelyezni egy kiválasztott
     * típusú objektumot az általa elfoglalt mezőre.
     * A parancs a következő esetekben sikeres:
     * - A kiválasztott játékos Mechanic, az argumentum Pipe,
     *   van nála cső, illetve az elfoglalt mező típusa Pump
     * - A kiválasztott játékos Mechanic, az argumentum Pump,
     *   van nála pumpa, illetve az elfoglalt mező típusa Pipe
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A lerakni kívánt objektum típusa
     */
    public static void Place(String[] args){
        boolean success = false;
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for Place",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for Place",false);
            return;
        }

        Mechanic player = Main.GetAsMechanic(Main.selectedPlayer);
        String playerName = Main.selectedPlayer;

        if(player == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        String part = args[1];

        if (part.equals("Pump") ){
            Pump tmp=player.GetControlledPump();
            player.PlacePump();
            if (player.GetControlledPump()!=tmp){
                success=true;

            }
        }
        else if (part.equals("Pipe")){
            int tmp= player.NumberOfEndpoints;
            player.PlacePipe();
            if (tmp!=player.NumberOfEndpoints) success=true;
        }
        else{
            Main.WriteIntoFilesAndConsole("Not valid argument",false);
            return;
        }

        if(success) Main.WriteIntoFilesAndConsole(String.format("%s placed %s successfully", playerName, part),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn’t place %s", playerName, part),false);

    }
    /**
     * A játékos megkísérel lecsatlakoztatni egy csövet az
     * általa elfoglalt mezőről. A lecsatlakoztatást csak
     * Mechanic képes elvégezni.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A lecsatlakoztatni kívánt cső azonosítója
     */
    public static void Disconnect(String[] args){
        boolean success = false;
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for Disconnect",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for Disconnect",false);
            return;
        }

        Mechanic player = Main.GetAsMechanic(Main.selectedPlayer);
        String playerName = Main.GetNameObs(player);

        if(player == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        String pipe = args[1];
        //Kedves Munkatársaim!
        // Kivételt dobott itt a szoftver hajnal 2 óra 1 perckor.
        // Üdv, KRK
        try{
            success = player.PickUpPipe((Pipe)Main.GetAsField(pipe));
        }
        catch (Exception e){
            success = false;
        }


        if(success) Main.WriteIntoFilesAndConsole(String.format("Pipe %s was disconnected by %s", pipe, playerName),false);
        else Main.WriteIntoFilesAndConsole(String.format("Pipe %s couldn’t be disconnected by %s", pipe, playerName),false);

    }


    private static boolean argCheck(String[] args, int goodValue, String lowValueResponse, String highValueResponse){
        if(args.length < goodValue){
            Main.WriteIntoFilesAndConsole(lowValueResponse,false);
            return false;
        }
        else if(args.length > goodValue){
            Main.WriteIntoFilesAndConsole(highValueResponse,false);
            return false;
        }

        return true;
    }
    /**
     * A kiválasztott játékos megkísérel debuffot elhelyezni az
     * általa elfoglalt mezőn. A parancs akkor sikeres, ha
     * az elfoglalt mező egy cső, illetve a kiválasztott játékos
     * típusa alapján elhelyezheti a kiválasztott debuffot.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: Az applikálni kívánt debuff típusa
     */
    public static void Debuff(String[] args){
        boolean success = false;
        if(!argCheck(args, 2, "Not enough arguments for Debuff", "Too many arguments for Debuff")) return;

        Mechanic mech = Main.GetAsMechanic(Main.selectedPlayer);
        Saboteur sabo=Main.GetAsSaboteur(Main.selectedPlayer);
        String playerName = Main.selectedPlayer;

        if(mech==null &&sabo==null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        String dtype=args[1];
        Field pos=null;
        if (dtype.equals(STICKY)){
            if (mech!=null){
                mech.MakeSticky();
                success=true;
            }
            else{
                sabo.MakeSticky();
                success=true;
            }
        }
        else if (dtype.equals(SLIPPERY) && sabo != null){
            sabo.MakeSlippery();
            success=true;
        }

        if (mech !=null){
            pos=mech.GetPosition();
        }
        else if (sabo!=null){
            pos=sabo.GetPosition();
        }
        String mezo=Main.GetNameObs(pos);

        if (mezo==null) mezo="*";
        if (!mezo.contains("pipe")) success=false;





        if(success) Main.WriteIntoFilesAndConsole(String.format("%s applied %s debuff to %s", playerName,args[1],Main.GetNameObs(pos)),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn’t apply %s debuff to %s", playerName,args[1],Main.GetNameObs(pos)),false);

    }
    /**
     * A kiválasztott játékos megkísérli az általa elfoglalt mező
     * megjavítását. A parancs akkor sikeres, ha az elfoglalt
     * mező a parancs lefutása végére megjavított állapotban lesz.
     * (Amennyiben a meghívás előtt nincs elromolva az elfoglalt mező,
     * a parancs sikeres lefutást mutat.)
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void Repair(String[] args){
        boolean success = false;
        if(args.length != 1){
            Main.WriteIntoFilesAndConsole("Incorrect number of arguments for Repair",false);
            return;
        }

        Mechanic player = Main.GetAsMechanic(Main.selectedPlayer);
        String playerName = Main.selectedPlayer;

        if(player == null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }

        player.Fix();
        if (player.GetPosition().IsBroken!=true){
            success=true;
        }
        if(success) Main.WriteIntoFilesAndConsole(String.format("%s successfully performed repair", playerName),false);
        else Main.WriteIntoFilesAndConsole(String.format("%s couldn’t perform repair", playerName),false);
    }
    /**
     * A játékos megkísérli az általa elfoglalt mezőn az
     * átfojás irányát átállítani. Amennyiben az elfoglalt mező egy pumpa,
     * illetve sikerült az átállítás, a parancs lefutása sikeres.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A beállítani kívánt bemeneti cső
     *             args[2]: A beállítani kívánt kimeneti cső
     */
    public static void ChangeFlow(String[] args){
        boolean success = false;
        if(args.length < 3){
            Main.WriteIntoFilesAndConsole("Not enough arguments for ChangeFlow",false);
            return;
        }
        else if(args.length > 3){
            Main.WriteIntoFilesAndConsole("Too many arguments for ChangeFlow",false);
            return;
        }

        Mechanic mech = Main.GetAsMechanic(Main.selectedPlayer);
        Saboteur sabo=Main.GetAsSaboteur(Main.selectedPlayer);
        String playerName = Main.selectedPlayer;

        if(mech==null && sabo==null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }
        if(!args[1].contains("pipe") || !args[2].contains("pipe")){
            Main.WriteIntoFilesAndConsole("Flow direction couldn’t be changed",false);
            return;
        }
        Pipe p1=(Pipe)Main.GetAsField(args[1]);
        Pipe p2=(Pipe)Main.GetAsField(args[2]);

        Player pl=null;
        if (sabo==null){
            mech.ChangePump(p1,p2);
            success=true;
            pl=Main.GetAsMechanic(Main.selectedPlayer);
        }
        else if(mech==null){
            sabo.ChangePump(p1,p2);
            success=true;
            pl=Main.GetAsSaboteur(Main.selectedPlayer);

        }


        if(success) Main.WriteIntoFilesAndConsole(String.format("Flow direction on Pump %s was changed to %s -> %s by %s\n", Main.GetNameObs(pl.GetPosition()),args[1],args[2],Main.selectedPlayer),false);
        else Main.WriteIntoFilesAndConsole(String.format("Flow direction couldn’t be changed by %s", Main.selectedPlayer),false);
    }
    /**
     * A parancs meghívása után minden kiadott parancs logolásra
     * kerül az argumentumként megadott fájlba.
     * A logolás szintjét a második argumentum adja meg:
     * [inputonly, all, outputonly]
     * A parancs sikeres, ha sikerült a logolás elindítása.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A fájl neve
     *             args[2]: A logolás szintje
     */
    public static void BeginSave(String[] args){
        boolean success = false;
        if(args.length < 3){
            Main.WriteIntoFilesAndConsole("Not enough arguments for BeginSave",false);
            return;
        }
        else if(args.length > 3){
            Main.WriteIntoFilesAndConsole("Too many arguments for BeginSave",false);
            return;
        }
        if (args[2].equals("inputonly")){
            Main.AddFiles(args[1],SaveMode.INPUTONLY);
            success=true;
        }
        else if (args[2].equals("all")){
            Main.AddFiles(args[1],SaveMode.ALL);
            success=true;
        }
        else if (args[2].equals("outputonly")){
            Main.AddFiles(args[1],SaveMode.OUTPUTONLY);
            success=true;
        }
        else{
            Main.WriteIntoFilesAndConsole("Not valid argument",false);
            return;
        }
        if(success) Main.WriteIntoFilesAndConsole(String.format("Started logging into file: %s, type: %s", args[1],args[2]),false);
        else Main.WriteIntoFilesAndConsole(String.format("Couldn’t start logging into file: %s", args[1]),false);
    }
    /**
     * Egy kiválasztott fájlból sorban lefuttat minden parancsot.
     * A parancs akkor sikeres, ha a fájl beolvasása sikerült.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A bemeneti fájl neve
     */
    public static void Load(String[] args){
        boolean success = false;
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for Load",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for Load",false);
            return;
        }
        Scanner src;
        try{
            File f=new File(args[1]);
            src=new Scanner(f);
        }
        catch (Exception ex){
            Main.WriteIntoFilesAndConsole("Couldn’t load file " + args[1],false);
            return;
        }

        Main.WriteIntoFilesAndConsole(String.format("Executing commands from file: %s", args[1]),false);
        while(src.hasNextLine()) {
            String tmp=src.nextLine();
            if (tmp.equals("") || tmp.equals(" ")) break;
            String []tmp2=tmp.split(" ");
            if (tmp.equals("exit")) {
                Main.interrupt = true;
                break;
            }
            Main.WriteIntoFilesAndConsole(tmp, true);
            Command c = Main.comms.get(tmp2[0]);
            if(c != null) c.execute(tmp2);
            else System.out.println("Hibás cmd.");
        }
        src.close();
    }
    /**
     * Kiírja a kiválasztott mező összes szomszédjának
     * összes lényeges tulajdonságát.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void ListNeighbors(String[] args){
        boolean success = false;
        if(args.length < 1){
            Main.WriteIntoFilesAndConsole("Not enough arguments for ListNeighbors",false);
            return;
        }
        else if(args.length > 1){
            Main.WriteIntoFilesAndConsole("Too many arguments for ListNeighbors",false);
            return;
        }
        Player pl=Main.GetAsMechanic(Main.selectedPlayer);
        if (pl==null){
            pl=Main.GetAsSaboteur(Main.selectedPlayer);
        }
        if (pl==null){
            Main.WriteIntoFilesAndConsole(NO_PLAYER,false);
            return;
        }
        try{
            Main.WriteIntoFilesAndConsole(String.format("Neighbours of %s", Main.GetNameObs(pl.GetPosition())),false);
            for (Field f: pl.GetPosition().GetNeighbors()){
                String type="";
                if(Main.GetNameObs(f).contains("pipe")){
                    type="Pipe";

                }
                else if(Main.GetNameObs(f).contains("pump")){
                    type="Pump";

                }
                else if(Main.GetNameObs(f).contains("source")){
                    type="Source";

                }
                else if(Main.GetNameObs(f).contains("drain")){
                    type="Drain";
                }
                Main.WriteIntoFilesAndConsole(String.format("%s: %s", Main.GetNameObs(f),type),false);
            }
        }
        catch (Exception ex){
            Main.WriteIntoFilesAndConsole(String.format("Couldn’t list the neighbours of %s",Main.GetNameObs(pl.GetPosition())),false);
        }

    }
    /**
     * Letrehoz egy új kiválasztott típusú objektumot.
     * Az újonnan létrehozott objektum azonosítója a
     * következő konvenció szerint kerül kiosztásra:
     * (típus)(sorszám)
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A létrehozni kívánt objektum típusa
     */
    public static void New(String[] args){
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for New",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for New",false);
            return;
        }
        String tmp=args[1];
        if(tmp.equals("Pipe")){
            new Pipe();
            Main.WriteIntoFilesAndConsole("New Pipe created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else if(tmp.equals("Pump")){
            new Pump();
            Main.WriteIntoFilesAndConsole("New Pump created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else if(tmp.equals("Source")){
            new Source();
            Main.WriteIntoFilesAndConsole("New Source created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else if(tmp.equals("Drain")){
            new Drain();
            Main.WriteIntoFilesAndConsole("New Drain created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else if(tmp.equals("Mechanic")){
            new Mechanic();
            Main.WriteIntoFilesAndConsole("New Mechanic created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else if (tmp.equals("Saboteur")){
            new Saboteur();
            Main.WriteIntoFilesAndConsole("New Saboteur created successfully: "+Main.obs.get(Main.obs.size()-1).ReturnFirst(),false);
        }
        else{
            Main.WriteIntoFilesAndConsole("Couldn’t create new"+tmp,false);
        }
    }
    /**
     * Kiírja egy kiválasztott objektum minden lényeges adatát.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A kiválasztott objektum azonosítója
     */
    public static void Details(String[] args){
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for Details",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for Details",false);
            return;
        }
        Field tmp;
        Player pl;
        if (args[1].contains("pipe")){
            tmp=(Pipe)Main.GetAsField(args[1]);
            if (tmp!=null){
                tmp.Details();
                return;
            }
        }
        else if (args[1].contains("pump")){
            tmp=(Pump)Main.GetAsField(args[1]);
            if (tmp!=null){
                tmp.Details();
                return;
            }
        }
        else if (args[1].contains("source")){
            tmp=(Source)Main.GetAsField(args[1]);
            if (tmp!=null){
                tmp.Details();
                return;
            }
        }
        else if (args[1].contains("drain")){
            tmp=(Drain)Main.GetAsField(args[1]);
            if (tmp!=null){
                tmp.Details();
                return;
            }
        }
        else if (args[1].contains("mechanic")){
            pl=(Mechanic)Main.GetAsMechanic(args[1]);
            if (pl!=null){
                pl.Details();
                return;
            }
        }
        else if (args[1].contains("saboteur")){
            pl=(Saboteur)Main.GetAsSaboteur(args[1]);
            if (pl!=null){
                pl.Details();
                return;
            }
        }
        else{
            Main.WriteIntoFilesAndConsole("Couldn’t get details for"+args[1],false);
        }

    }
    /**
     * Kiírja az összes létező objektum összes lényeges adatát.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void ListObjects(String[] args){
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for ListNeighbors",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for ListNeighbors",false);
            return;
        }
        if (Main.obs.size()==0){
            Main.WriteIntoFilesAndConsole("No objects exist",false);
            return;
        }
        for (var f :Main.obs){

            if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Pump")){

                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Pump)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Pipe")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Pipe)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Drain")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Drain)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Source")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Source)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Mechanic")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Mechanic)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("Saboteur")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((Saboteur)f.ReturnSecond()).Details();
            }
            else if (f.ReturnFirst().contains(args[1].toLowerCase()) && args[1].equals("GameController")){
                Main.WriteIntoFilesAndConsole("The details of "+f.ReturnFirst(),false);
                ((GameController)f.ReturnSecond()).Details();
            }
        }

    }
    /**
     * Beállítja a kiválasztott játékost.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: A kiválasztani kívánt játékos azonosítója
     */
    public static void SetPlayer(String[] args){
        if(args.length < 2){
            Main.WriteIntoFilesAndConsole("Not enough arguments for SetPlayer",false);
            return;
        }
        else if(args.length > 2){
            Main.WriteIntoFilesAndConsole("Too many arguments for SetPlayer",false);
            return;
        }
        Player p=Main.GetAsMechanic(args[1]);

        if (p==null){
            p=Main.GetAsSaboteur(args[1]);
        }
        if (p==null){
            Main.WriteIntoFilesAndConsole("Couldn’t switch to player:"+args[1],false);
            return;
        }
        Main.selectedPlayer=args[1];
        Main.WriteIntoFilesAndConsole("Currently selected player:"+args[1],false);
    }

    private static String GetNextField(String before_field, String pipe){
        String out = null;
        var p = ((Pipe)Main.GetObjRefObs(pipe));
        if(p == null) return null;
        for(var n : p.GetNeighbors()){
            if(!Main.GetNameObs(n).equals(before_field)) out = Main.GetNameObs(n);
        }
        return out;
    }

    private static void ChangePumpDistInList(ArrayList<Tuple<String, Integer>> pumps, String name, int val){
        for (Tuple<String, Integer> a : pumps){
            if(a.ReturnFirst().equals(name)) a.y = val;
        }
    }
    /**
     * Az összes léptethető objetumon meghívja a Step() függvényt.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void Step(String[] args){
        if(args.length < 1){
            Main.WriteIntoFilesAndConsole("Not enough arguments for ListNeighbors",false);
            return;
        }
        else if(args.length > 1){
            Main.WriteIntoFilesAndConsole("Too many arguments for ListNeighbors",false);
            return;
        }
        if (Main.obs.size()==0){
            Main.WriteIntoFilesAndConsole("No steppable object exist",false);
            return;
        }

        Main.WriteIntoFilesAndConsole("Calling step on every active object:",false);

        // Sorting Steppables by type
        ArrayList<String> drains = new ArrayList<>();
        ArrayList<String> sources = new ArrayList<>();
        ArrayList<String> pipes = new ArrayList<>();
        ArrayList<Tuple<String, Integer>> pumps = new ArrayList<>();

        for (var v :Main.obs){
            Steppable f=Main.GetAsSteppable(v.ReturnFirst());
            if(f != null) {
                String type = Main.GetNameObs(f).replaceAll("\\d","");
                switch (type) {
                    case "pipe" -> pipes.add(Main.GetNameObs(f));
                    case "pump" -> pumps.add(new Tuple<>(Main.GetNameObs(f), 100));
                    case "drain" -> drains.add(Main.GetNameObs(f));
                    case "source" -> sources.add(Main.GetNameObs(f));
                };
            }
        }

        // Stepping all Drains
        for(String a : drains){
            ((Drain)Main.GetObjRefObs(a)).Step();
            Main.WriteIntoFilesAndConsole("\t[STEP] "+a,false);
        }

        // Stepping all Pipes
        for(String a : pipes){
            ((Pipe)Main.GetObjRefObs(a)).Step();
            Main.WriteIntoFilesAndConsole("\t[STEP] "+a,false);
        }


        for(String d_s : drains){
            Drain d = (Drain)Main.GetObjRefObs(d_s);
            ArrayList<Field> neighbors = d.GetNeighbors();
            if(neighbors == null) continue;
            for(Field f : neighbors){
                if(Main.GetNameObs(f).contains("pipe")){
                    int depth = 1;
                    String next = GetNextField(d_s, Main.GetNameObs(f));
                    if(next == null) continue;
                    if (next.contains("pump") && ((Pump)Main.GetObjRefObs(next)).GetDst() != null && Main.GetNameObs(((Pump)Main.GetObjRefObs(next)).GetDst()).equals(Main.GetNameObs(f))){
                        ChangePumpDistInList(pumps, next, depth);
                    }
                    else continue;
                    String src_pipe = Main.GetNameObs(((Pump)Main.GetObjRefObs(next)).GetSrc());
                    String previous = next;
                    while(src_pipe != null && GetNextField(previous, src_pipe) != null && GetNextField(previous, src_pipe).contains("pump") && ((Pump)Main.GetObjRefObs(GetNextField(previous, src_pipe))).GetDst() != null && Main.GetNameObs(((Pump)Main.GetObjRefObs(GetNextField(previous, src_pipe))).GetDst()).equals(src_pipe)){
                        depth += 1;
                        previous = GetNextField(previous, src_pipe);
                        ChangePumpDistInList(pumps, previous, depth);
                        src_pipe = Main.GetNameObs(((Pump)Main.GetObjRefObs(previous)).GetSrc());
                    }
                }
            }
        }

        Collections.sort(pumps, (a, b) -> a.ReturnSecond().compareTo(b.ReturnSecond()));

        for(Tuple<String, Integer> a : pumps){
            System.out.println(a.ReturnSecond());
            ((Pump)Main.GetObjRefObs(a.ReturnFirst())).Step();
            Main.WriteIntoFilesAndConsole("\t[STEP] "+a.ReturnFirst(),false);
        }

        // Stepping all Sources
        for(String a : sources){
            ((Source)Main.GetObjRefObs(a)).Step();
            Main.WriteIntoFilesAndConsole("\t[STEP] "+a,false);
        }

        /* if (f!=null){
            Main.WriteIntoFilesAndConsole("\t[STEP] "+v.ReturnFirst(),false);
            f.Step();
        } */
        // YEAH BOI
        GameController.getInstance().Step();
    }

    public static void NewGame(String[] args){
        GameController.getInstance().NewGame(Integer.parseInt(args[1]));

        Main.WriteIntoFilesAndConsole("New Game succesfully created!",false);
    }
    /**
     * Kiírja az összes parancsot, illetve azok helyes használatát.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void Help(String[] args) {
       Main.WriteIntoFilesAndConsole("RequestPart <comp>\n"+"Leírás: Alkatrész kérése egy Field-től.\n" +
                                            "Opciók: <comp>: pipe: A játékos csövet kér.\n" +
                                             "pump: A játékos pumpát kér.",false);
        Main.WriteIntoFilesAndConsole("Move <to>\n"+
                "Leírás: A játékos a kiválasztott mezőre lép.\n"+
                "Opciók: <to>: Célmező",false);
        Main.WriteIntoFilesAndConsole("Sabotage\n" +
                "Leírás: A kiválasztott játékos szabotálja a mezőt, amin áll.", false);

        Main.WriteIntoFilesAndConsole("Place <comp>\n" +
                "Leírás: A kiválasztott játékos lehelyez egy alkatrészt a pályan, ami jelenleg a birtokában van.\n" +
                "Opciók: <comp>: pipe: Egy csövet próbál lehelyezni.\n" +
                "pump: Egy pumpát próbál lehelyezni.", false);

        Main.WriteIntoFilesAndConsole("Disconnect <pipe>\n" +
                "Leírás: Lecsatolja az adott csövet a kiválasztott játékos által elfoglalt objektumról.\n" +
                "Opciók: <pipe>: A lecsatlakoztatni kívánt cső.", false);

        Main.WriteIntoFilesAndConsole("Debuff <debuff>\n" +
                "Leírás: A kiválasztott játékos debuffot (csúszós/ragadós) helyez a mezőre, amin áll.\n" +
                "Opciók: <debuff>: sticky: Cső ragadóssá tétele\n" +
                "slippery: Cső csúszóssá tétele", false);

        Main.WriteIntoFilesAndConsole("Repair\n" +
                "Leírás: A kiválasztott játékos megszereli a mezőt, amin áll.", false);

        Main.WriteIntoFilesAndConsole("ChangeFlow <src> <dest>\n" +
                "Leírás: A játékos megváltoztatja a pumpa folyását, amin áll\n" +
                "Opciók: <src>: Bemeneti cső\n" +
                "<dest>: Kimeneti cső", false);

        Main.WriteIntoFilesAndConsole("BeginSave <file> <log_type>\n" +
                "Leírás:A program a konzolra írt parancsokat elmenti egy paraméterként megadott fájlba.\n" +
                "Opciók: <file>: Fájl neve\n" +
                "<log_type>: (inputonly, all, outputonly)", false);

        Main.WriteIntoFilesAndConsole("Load <file>\n" +
                "Leírás: A program a fájlban lévő parancsokat sorban végrehajtja.\n" +
                "Opciók: <file>: Fájl neve", false);

        Main.WriteIntoFilesAndConsole("ListNeighbors\n" +
                "Leírás: A program felsorolja a jelenleg kiválasztott játékos által elfoglalt komponens szomszédjait.", false);


        Main.WriteIntoFilesAndConsole("New <obj>\n" +
                "Leírás: Létrejön egy új komponens/játékos a kiválasztott típussal.\n" +
                "Opciók: <obj>: A létrehozandó objektum típusa. Lehetséges értékek: " +
                "(pipe, pump, source, drain, saboteur, mechanic, gamecontroller)", false);

        Main.WriteIntoFilesAndConsole("Details <obj_name>\n" +
                "Leírás: Kiírja egy kiválasztott objektumról a hasznos információkat.\n" +
                "Opciók: <obj_name>: A lekérdezett objektum neve.", false);

        Main.WriteIntoFilesAndConsole("ListObjects <type>\n" +
                "Leírás: Kilistázza az aktív objektumokat Opcionálisan megadható, hogy milyen típusú " +
                "(pipe, pump, source, drain, mechanic, saboteur) objektumokat listázza csak ki.\n" +
                "Opciók: <type>: A listázandó objektumok típusa. Lehetséges értékek: " +
                "(pipe, pump, source, drain, saboteur, mechanic)", false);

        Main.WriteIntoFilesAndConsole("SetPlayer <player>\n" +
                "Leírás: Beállítja az aktív játékost, aminek a nevében futnak majd a parancsok.\n" +
                "Opciók: <player>: Kiválasztott játékos", false);

        Main.WriteIntoFilesAndConsole("Step\n" +
                "Leírás: Meghívja az összes objektum Step függvényét.", false);

        Main.WriteIntoFilesAndConsole("Help\n" +
                "Leírás: Kiírja az elérhető parancsok leirását, paramétereit.", false);

        Main.WriteIntoFilesAndConsole("Destroy\n" +
                "Leírás: Minden megsemmisül.", false);

        Main.WriteIntoFilesAndConsole("SetAttribute <obj_name> <attr_name> [attr_opt_0, ..., attr_opt_n]\n" +
                "Leírás: Beállítja a megadott objektum megadott paramétereit.\n" +
                "Opciók: <obj_name>: A kiválasztott objektum.\n" +
                "<attr_name>:A beállítani kívánt attribútum.\n" +
                "<attr_opt_x>:Az attribútum beállítandó értékei.", false);

        Main.WriteIntoFilesAndConsole("SetRandom <true/false>\n" +
                "Leírás: Engedélyezi / letiltja a random értékek használatát.", false);


    }
    /**
     * Az összes létező objektumot törli.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             Nincs szükséges bemeneti argumentum
     */
    public static void Destroy(String[] args){
        for (var ob : Main.obs){
            Main.WriteIntoFilesAndConsole(Main.GetNameObs(ob.ReturnSecond())+" destroyed",false);
        }
        try{
            Main.obs.clear();
            Main.fields.clear();
            Main.mechanics.clear();
            Main.saboteurs.clear();
            Main.steppables.clear();
            Main.selectedPlayer="";
            GameController.Destroy();
        }
        catch (Exception ex){
            Main.WriteIntoFilesAndConsole("Couldn’t destroy anything",false);
        }


    }
    /**
     * Beállítja a kiválasztott objektum kiválasztott attribútumának értékét.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: Beállítani kívánt objektum.
     *             args[2]: A beállítani kívánt argumentum.
     *             args[3..n]: A kiválasztott argumentumhoz tartozó beállítások.
     */
    public static void SetAttribute(String[] args){
        boolean success = false;

        if(args.length < 3){
            System.out.println("Not enough arguments for SetAttribute");
            return;
        }

        Field f = Main.GetAsField(args[1]);
        Mechanic mech = Main.GetAsMechanic(args[1]);
        Saboteur sab = Main.GetAsSaboteur(args[1]);
        Steppable step = Main.GetAsSteppable(args[1]);

        if(f != null){
            // Field
            if(args[2].equals("IsBroken")){
                // Field isBroken
                if(args.length == 4){
                    if(args[3].equals("true")){
                        f.SetIsBroken(true);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "IsBroken", "true", args[1]),
                                false);
                        return;
                    }
                    else if(args[3].equals("false")){
                        f.SetIsBroken(false);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "IsBroken", "false", args[1]),
                                false);
                        return;
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "IsBroken", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "IsBroken", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("Neighbors")){
                // Field Neighbors
                if(args.length == 5){
                    if(args[3].equals("add")){
                        Field added = Main.GetAsField(args[4]);
                        if(added != null){
                            // Added successfully
                            f.AddNeighbour(added);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("%s added to %s of %s", args[4], "Neighbors", args[1]),
                                    false);
                            return;
                        }
                        else{
                            // Couldn't add
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't add %s to %s of %s", args[4], "Neighbors", args[1]),
                                    false);
                            return;
                        }
                    }
                    else if(args[3].equals("remove")){
                        Field added = Main.GetAsField(args[4]);
                        if(added != null){
                            // Remove successfully
                            f.Neighbors.remove(added);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("%s removed from %s of %s", args[4], "Neighbors", args[1]),
                                    false);
                            return;
                        }
                        else{
                            // Couldn't remove
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't remove %s from %s of %s", args[4], "Neighbors", args[1]),
                                    false);
                            return;
                        }
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Neighbors", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "Neighbors", args[1]),
                            false);
                    return;
                }
            }
            if(Main.GetNameObs(f).contains("pipe")){
                Pipe p = (Pipe) f;
                // Pipe
                if(args[2].equals("Occupied")){
                    // Occupied
                    if(args.length == 4){
                        if(args[3].equals("true")){
                            p.Occupied = true;
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "Occupied", "true", args[1]),
                                    false);
                            return;
                        }
                        else if(args[3].equals("false")){
                            p.Occupied = false;
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "Occupied", "false", args[1]),
                                    false);
                            return;
                        }
                        else{
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "Occupied", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Occupied", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("HasWater")){
                    // HasWater
                    if(args.length == 4){
                        if(args[3].equals("true")){
                            p.SetHasWater(true);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "HasWater", "true", args[1]),
                                    false);
                            return;
                        }
                        else if(args[3].equals("false")){
                            p.SetHasWater(false);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "HasWater", "false", args[1]),
                                    false);
                            return;
                        }
                        else{
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "HasWater", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "HasWater", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals(SLIPPERY)){
                    // Slippery
                    if(args.length == 4){
                        if(args[3].equals("true")){
                            p.SetSlippery(true);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", SLIPPERY, "true", args[1]),
                                    false);
                            return;
                        }
                        else if(args[3].equals("false")){
                            p.SetSlippery(false);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", SLIPPERY, "false", args[1]),
                                    false);
                            return;
                        }
                        else{
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", SLIPPERY, args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", SLIPPERY, args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals(STICKY)){
                    // Sticky
                    if(args.length == 4){
                        if(args[3].equals("true")){
                            p.SetSticky(true);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", STICKY, "true", args[1]),
                                    false);
                            return;
                        }
                        else if(args[3].equals("false")){
                            p.SetSticky(false);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", STICKY, "false", args[1]),
                                    false);
                            return;
                        }
                        else{
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", STICKY, args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", STICKY, args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("DebuffTime")){
                    // DebuffTime
                    if(args.length == 4){
                        try{
                            int a = Integer.parseInt(args[3]);
                            p.SetDebuffTimer(a);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "DebuffTime", args[3], args[1]),
                                    false);
                            return;
                        }
                        catch (Exception e) {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "DebuffTime", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "DebuffTime", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                            false);
                    return;
                }
            }
            else if(Main.GetNameObs(f).contains("pump")){
                // Pump
                Pump p = (Pump) f;
                if(args[2].equals("Src")){
                    // Src: Field
                    if(args.length == 4){
                        Field added = Main.GetAsField(args[3]);
                        if(p.IsNeighbor(added)){
                            p.SetSrc(added);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "Src", args[3], args[1]),
                                    false);
                            return;
                        }
                        else {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "Src", args[1]),
                                    false);
                            return;
                        }
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Src", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("Dst")){
                    // Dst: Field
                    if(args.length == 4){
                        Field added = Main.GetAsField(args[3]);
                        if(p.IsNeighbor(added)){
                            p.SetDst(added);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "Dst", args[3], args[1]),
                                    false);
                            return;
                        }
                        else {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "Dst", args[1]),
                                    false);
                            return;
                        }
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Dst", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("Buffer")){
                    // Buffer: int
                    if(args.length == 4){
                        try{
                            int a = Integer.parseInt(args[3]);
                            p.SetBuffer(a);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "Buffer", args[3], args[1]),
                                    false);
                            return;
                        }
                        catch (Exception e) {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "Buffer", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Buffer", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("BufferCap")){
                    // BufferCap: int
                    if(args.length == 4){
                        try{
                            int a = Integer.parseInt(args[3]);
                            p.SetBufferCap(a);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "BufferCap", args[3], args[1]),
                                    false);
                            return;
                        }
                        catch (Exception e) {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "BufferCap", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "BufferCap", args[1]),
                                false);
                        return;
                    }
                }
                else if(args[2].equals("TicksUntilBreak")){
                    // TIcksUntilBreak: int
                    if(args.length == 4){
                        try{
                            int a = Integer.parseInt(args[3]);
                            p.TicksUntilBreak = a;
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "TicksUntilBreak", args[3], args[1]),
                                    false);
                            return;
                        }
                        catch (Exception e) {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "TicksUntilBreak", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "TicksUntilBreak", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                            false);
                    return;
                }
            }
            else if(Main.GetNameObs(f).contains("source")){
                // Source
                Main.WriteIntoFilesAndConsole(
                        String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                        false);
                return;
            }
            else if(Main.GetNameObs(f).contains("drain")){
                // Drain
                Drain d = (Drain) f;
                if(args[2].equals("PipeCounter")){
                    // PipeCounter
                    if(args.length == 4){
                        try{
                            int a = Integer.parseInt(args[3]);
                            d.SetPipeCounter(a);
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Attribute %s changed to %s on %s", "PipeCounter", args[3], args[1]),
                                    false);
                            return;
                        }
                        catch (Exception e) {
                            Main.WriteIntoFilesAndConsole(
                                    String.format("Couldn't change attribute %s on %s", "PipeCounter", args[1]),
                                    false);
                            return;
                        }
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "PipeCounter", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                            false);
                    return;
                }
            }
        }
        else if(mech != null){
            // Mechanic
            if(args[2].equals("Position")){
                // Position
                if(args.length == 4){
                    Field added = Main.GetAsField(args[3]);
                    if(added != null){
                        mech.SetPosition(added);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Position", args[3], args[1]),
                                false);
                        return;
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Position", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "Position", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("ControlledPump")){
                // ControlledPump
                if(args.length == 4){
                    Field added = Main.GetAsField(args[3]);
                    if(added != null && args[3].contains("pump")){
                        mech.SetControlledPump((Pump)added);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "ControlledPump", args[3], args[1]),
                                false);
                        return;
                    }
                    else if(args[3].equals("null")){
                        mech.SetControlledPump(null);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "ControlledPump", args[3], args[1]),
                                false);
                        return;
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "ControlledPump", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "ControlledPump", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("ControlledPipe")){
                // ControlledPipe
                if(args.length == 4){
                    Field added = Main.GetAsField(args[3]);
                    if(added != null && args[3].contains("pipe")){
                        mech.SetControlledPipe((Pipe)added);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "ControlledPipe", args[3], args[1]),
                                false);
                        return;
                    }
                    else if(args[3].equals("null")){
                        mech.SetControlledPipe(null);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "ControlledPipe", args[3], args[1]),
                                false);
                        return;
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "ControlledPipe", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "ControlledPipe", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("Stuck")){
                // Stuck
                if(args.length == 4){
                    if(args[3].equals("true")){
                        mech.SetStuck(true);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Stuck", "true", args[1]),
                                false);
                        return;
                    }
                    else if(args[3].equals("false")){
                        mech.SetStuck(false);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Stuck", "false", args[1]),
                                false);
                        return;
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Stuck", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "Stuck", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("NumberOfEndpoints")){
                // NumberOfEndpoints
                if(args.length == 4){
                    try{
                        int a = Integer.parseInt(args[3]);
                        mech.NumberOfEndpoints = a;
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "NumberOfEndpoints", args[3], args[1]),
                                false);
                        return;
                    }
                    catch (Exception e) {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "NumberOfEndpoints", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "NumberOfEndpoints", args[1]),
                            false);
                    return;
                }
            }
            else{
                Main.WriteIntoFilesAndConsole(
                        String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                        false);
                return;
            }
        }
        else if(sab != null){
            // Saboteur
            if(args[2].equals("Position")){
                // Position
                if(args.length == 4){
                    Field added = Main.GetAsField(args[3]);
                    if(added != null){
                        sab.SetPosition(added);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Position", args[3], args[1]),
                                false);
                        return;
                    }
                    else {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Position", args[1]),
                                false);
                        return;
                    }
                }
                else {
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "Position", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("Stuck")){
                // Stuck
                if(args.length == 4){
                    if(args[3].equals("true")){
                        sab.SetStuck(true);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Stuck", "true", args[1]),
                                false);
                        return;
                    }
                    else if(args[3].equals("false")){
                        sab.SetStuck(false);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "Stuck", "false", args[1]),
                                false);
                        return;
                    }
                    else{
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "Stuck", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "Stuck", args[1]),
                            false);
                    return;
                }
            }
            else{
                Main.WriteIntoFilesAndConsole(
                        String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                        false);
                return;
            }
        }
        else if(step == GameController.getInstance()){
            // GameController
            GameController gc = (GameController) step;
            if(args[2].equals("GameTime")){
                // GameTime
                if(args.length == 4){
                    try{
                        int a = Integer.parseInt(args[3]);
                        gc.setGameTime(a);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "GameTime", args[3], args[1]),
                                false);
                        return;
                    }
                    catch (Exception e) {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "GameTime", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "GameTime", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("MaxGameTime")){
                // MaxGameTime
                if(args.length == 4){
                    try{
                        int a = Integer.parseInt(args[3]);
                        gc.setMaxGameTime(a);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "MaxGameTime", args[3], args[1]),
                                false);
                        return;
                    }
                    catch (Exception e) {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "MaxGameTime", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "MaxGameTime", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("LeakedWater")){
                // LeakedWater
                if(args.length == 4){
                    try{
                        int a = Integer.parseInt(args[3]);
                        gc.setLeakedWater(a);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "LeakedWater", args[3], args[1]),
                                false);
                        return;
                    }
                    catch (Exception e) {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "LeakedWater", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "LeakedWater", args[1]),
                            false);
                    return;
                }
            }
            else if(args[2].equals("CollectedWater")){
                // CollectedWater
                if(args.length == 4){
                    try{
                        int a = Integer.parseInt(args[3]);
                        gc.setCollectedWater(a);
                        Main.WriteIntoFilesAndConsole(
                                String.format("Attribute %s changed to %s on %s", "CollectedWater", args[3], args[1]),
                                false);
                        return;
                    }
                    catch (Exception e) {
                        Main.WriteIntoFilesAndConsole(
                                String.format("Couldn't change attribute %s on %s", "CollectedWater", args[1]),
                                false);
                        return;
                    }
                }
                else{
                    Main.WriteIntoFilesAndConsole(
                            String.format("Couldn't change attribute %s on %s", "CollectedWater", args[1]),
                            false);
                    return;
                }
            }
            else{
                Main.WriteIntoFilesAndConsole(
                        String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                        false);
                return;
            }
        }

        Main.WriteIntoFilesAndConsole(
                String.format("Couldn't change attribute %s on %s", args[2], args[1]),
                false);
    }
    /**
     * Elgedélyezi/letiltja a random működést.
     * Amennyiben a parancs sikeres, a sikeres lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * Amennnyiben a parancs nem sikeres, a sikertelen lefutás ténye
     * logolásra kerül az összes megnyitott fájlban, illetve
     * a konzolra is kiírásra kerül.
     * @param args A bemeneti argumentumok
     *             args[1]: true/false
     *
     */
    public static void SetRandom(String[] args){
        boolean success = false;
        if(args.length < 2){
            System.out.println("Not enough arguments for SetRandom");
            return;
        }
        else if(args.length > 2){
            System.out.println("Too many arguments for SetRandom");
            return;
        }
        if(args[1].equals("enabled")){
            Main.isRandomEnabled = true;
            success = true;
        }
        else if (args[1].equals("disabled")){
            Main.isRandomEnabled = false;
            success = true;
        }
        else{
            System.out.println("Invalid argument for SetRandom");
            return;
        }
        if(success) System.out.println("Randomization " + args[1]);
        else System.out.println("Couldn't " + args[1] + " randomization");
    }
}