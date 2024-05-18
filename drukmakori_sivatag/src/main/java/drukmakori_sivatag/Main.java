package drukmakori_sivatag;

import java.io.*;
import java.util.*;
public class Main {

    static Game game;
    static List<Tuple<String, Object>> obs = new LinkedList<>();
    static HashMap<Object, Field> fields = new HashMap<Object, Field>();
    static HashMap<Object, Mechanic> mechanics = new HashMap<Object, Mechanic>();
    static HashMap<Object, Saboteur> saboteurs = new HashMap<Object, Saboteur>();
    static HashMap<Object, Steppable> steppables = new HashMap<Object, Steppable>();
    static HashMap<Object, Command> comms = new HashMap<Object, Command>();

    static List<Tuple<FileWriter, SaveMode>> files_save = new LinkedList<>();
    static Random random = new Random();
    static boolean isRandomEnabled = true;

    // Majd
    void ExecuteCommand(String comm){
        //What the HEEEEEEEEEEEEELLLLL!!!! WHO IS THAT MAN... JUST GIVE ME A NAME MAAN... OoooOooOOOoooOOOooOoO MA' GOD
    }
    /**
     * Adds the specified name and object to the observation list.
     *
     * @param name   the name to be added
     * @param object the object to be added
     */
    static void AddToObs(String name, Object object){
        obs.add(new Tuple<String, Object>(name, object));
    }
    static String GetNameObs(Object ref){
        for(Tuple<String, Object> a : obs){
            if(a.ReturnSecond() == ref){
                return a.ReturnFirst();
            }
        }
        return null;
    }
    /**
     * Retrieves the object reference associated with the given name from the observation list.
     *
     * @param name the name to search for
     * @return the object reference associated with the name, or null if not found
     */
    static Object GetObjRefObs(String name){
        for(Tuple<String, Object> a : obs){
            if(a.ReturnFirst().equals(name)){
                return a.ReturnSecond();
            }
        }
        return null;
    }
    /**
     * Adds a file with the specified name and save mode to the file save list.
     *
     * @param file_name the name of the file to be added
     * @param sm        the save mode for the file
     * @return true if the file was successfully added, false otherwise
     */
    static boolean AddFiles(String file_name, SaveMode sm){
        try {
            FileWriter fw = new FileWriter(file_name);
            files_save.add(new Tuple<>(fw, sm));
            return true;
        } catch (IOException e) {
            return false;
        }
    }
    /**
     * Writes the specified string into files and/or console based on the given parameters.
     *
     * @param string   the string to be written
     * @param isInput  a flag indicating if the string is an input or output
     */
    static void WriteIntoFilesAndConsole(String string, boolean isInput){
        if(!isInput) System.out.println(string);
        for(Tuple<FileWriter, SaveMode> a : files_save){
            if(isInput && (a.ReturnSecond() == SaveMode.ALL || a.ReturnSecond() == SaveMode.INPUTONLY)){
                try {
                    a.ReturnFirst().write(string);
                    a.ReturnFirst().write("\n");
                } catch (IOException e) {
                    System.out.println("Couldn't write into file");
                }
            }
            else if(!isInput && (a.ReturnSecond() == SaveMode.ALL || a.ReturnSecond() == SaveMode.OUTPUTONLY)){
                try {
                    a.ReturnFirst().write(string);
                    a.ReturnFirst().write("\n");
                } catch (IOException e) {
                    System.out.println("Couldn't write into file");
                }
            }
        }
    }
    /**
     * Retrieves the specified object as a Mechanic using the given name.
     *
     * @param name the name associated with the object
     * @return the object as a Mechanic, or null if not found or not a Mechanic
     */
    static Mechanic GetAsMechanic(String name){
        return mechanics.get(GetObjRefObs(name));
    }
    /**
     * Retrieves the specified object as a Saboteur using the given name.
     *
     * @param name the name associated with the object
     * @return the object as a Saboteur, or null if not found or not a Saboteur
     */
    static Saboteur GetAsSaboteur(String name){
        return saboteurs.get(GetObjRefObs(name));
    }
    /**
     * Retrieves the specified object as a Field using the given name.
     *
     * @param name the name associated with the object
     * @return the object as a Field, or null if not found or not a Field
     */
    static Field GetAsField(String name){
        return fields.get(GetObjRefObs(name));
    }
    /**
     * Retrieves the specified object as a Steppable using the given name.
     *
     * @param name the name associated with the object
     * @return the object as a Steppable, or null if not found or not a Steppable
     */
    static Steppable GetAsSteppable(String name){
        return steppables.get(GetObjRefObs(name));
    }

    static String selectedPlayer = "";
    static boolean interrupt = false;
    static void _init() {
        comms.put("RequestPart", Functions::RequestPart);
        comms.put("Move", Functions::Move);
        comms.put("Sabotage", Functions::Sabotage);
        comms.put("Place", Functions::Place);
        comms.put("Disconnect", Functions::Disconnect);
        comms.put("Debuff", Functions::Debuff);
        comms.put("Repair", Functions::Repair);
        comms.put("ChangeFlow", Functions::ChangeFlow);
        comms.put("BeginSave", Functions::BeginSave);
        comms.put("Load", Functions::Load);
        comms.put("ListNeighbors", Functions::ListNeighbors);
        comms.put("New", Functions::New);
        comms.put("Details", Functions::Details);
        comms.put("ListObjects", Functions::ListObjects);
        comms.put("SetPlayer", Functions::SetPlayer);
        comms.put("Step", Functions::Step);
        comms.put("Help", Functions::Help);
        comms.put("Destroy", Functions::Destroy);
        comms.put("SetAttribute", Functions::SetAttribute);
        comms.put("SetRandom", Functions::SetRandom);
        comms.put("NewGame",Functions::NewGame);
    }
    /**
     * Reads a line from the specified BufferedReader and splits it into an array of strings.
     *
     * @param br the BufferedReader to read from
     * @return an array of strings containing the individual words from the input line
     */
    public static String[] Olvas(BufferedReader br) {
        String temp;
        try {
            temp = br.readLine();
        }
        catch(IOException e) {
            temp = "";
        }
        return temp.split(" ");
    }

    /**
     * The main method of the program.
     *
     * @param args the command line arguments
     */
    public static void main(String [] args){
       BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
       _init();
       game = new Game();
       boolean exit = false;
        String[] input = Olvas(br);
        while(!input[0].equals("exit")) {

            Command c = comms.get(input[0]);
            if(c != null) c.execute(input);
            else System.out.println("Hibás cmd.");

            if(interrupt) break;
            System.out.print("> ");
            input = Olvas(br);
            String b = "";
            for(String a : input){
                b = b.concat(a);
            }
            WriteIntoFilesAndConsole(b, true);
        }
        WriteIntoFilesAndConsole("exit", true);
        for(Tuple<FileWriter, SaveMode> a : files_save){
            try {
                a.ReturnFirst().close();
            } catch (IOException e) {
                System.out.println("hEHEH");
            }
        }
    }
}