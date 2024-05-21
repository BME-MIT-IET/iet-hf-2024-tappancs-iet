package drukmakori_sivatag;

import drukmakori_sivatag.BaseShape;
import jdk.jfr.Enabled;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class GameView extends JPanel {
    private BaseShape selected=null;
    boolean UserInputNeeded=false;
    private JPanel canvas = new JPanel(){
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            for(var a : lista){
                a.playersDrawn=1;
                a.Draw(g);
            }

            int xmp=800;
            int ymp=450;
            DrawDetails(g,xmp,ymp);
            DrawUserInput(g);

        }
    };

    //Na itt a kert!
    private ArrayList<Player> PlayerFIFO = new ArrayList<>();
    //no itt jegyezzük, hogy körön belül hogy állunk.
    private int numOfTurnPart = 0;
    private ArrayList<JButton> buttons = new ArrayList<>();
    private JPanel button_container = new JPanel(new GridLayout(2, 13, 0, 0));
    private Game game;
    private LinkedList<BaseShape> lista = new LinkedList<>();

    private boolean move_selection = false;
    private boolean disconnect_selection = false;
    private boolean change_flow_selection_1 = false;
    private boolean change_flow_selection_2 = false;
    private boolean place_pump_selection = false;
    private Point pump_point = null;

    private BaseShape flow_input = null;

    public static HashMap<String, BaseShape> connections = new HashMap<>();
    private JButton bEndRound = new JButton("End Round");
    private JButton bRepair = new JButton("Repair");
    private JButton bSabotage = new JButton("Sabotage");
    private JButton bMakeSticky = new JButton("Sticky");
    private JButton bMakeSlippery = new JButton("Slippery");
    private JButton bMove = new JButton("Move");
    private JButton bPickUpPipe = new JButton("PickUpPipe");
    private JButton bPickUpPump = new JButton("PickUpPump");
    private JButton bDisconnect = new JButton("Disconnect");
    private JButton bChangeFlow = new JButton("ChangeFlow");
    private JButton bPlacePump = new JButton("PlacePump");
    private JButton bPlacePipe = new JButton("PlacePipe");
    private JButton bBackToMenu = new JButton("Back to menu");

    private int akcioPont;

    /**
     * A GameView osztály felelős a játék grafikus felületének kezeléséért és az interakciókért.
     * Ez a konstruktor inicializálja a GameView objektumot a megadott játékobjektummal.
     * Beállítja a háttérszínt, elrendezést és hozzáadja a gombokat a felülethez.
     * Minden gombhoz egyedi eseménykezelőket rendel, amelyek a gomb megnyomásakor meghívják
     * a megfelelő funkciókat vagy állapotváltoztatásokat a játékban.
     * Az eseménykezelők végrehajtása után frissíti a nézetet, hogy az aktuális állapotot tükrözze.
     * A felülethez hozzáadja a rajzvásznat és a gombkonténert.
     * Ezenkívül beállítja a kurzor figyelőjét az egérkattintások kezeléséhez a rajzvásznon.
     *
     * @param _game A játék objektum, amelyet a GameView inicializálásához használunk.
     *              Ez az objektum tartalmazza az összes játéklogikát és állapotot.
     */
    public GameView(Game _game){
        game = _game;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        bEndRound.addActionListener((ActionEvent e) -> {
            TurnusLeptetes();
        });
        bRepair.addActionListener((ActionEvent e) -> {
            Functions.Repair("Repair".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bSabotage.addActionListener((ActionEvent e) -> {
            Functions.Sabotage("Sabotage".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bMakeSlippery.addActionListener((ActionEvent e) -> {
            Functions.Debuff("Debuff Slippery".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bMakeSticky.addActionListener((ActionEvent e) -> {
            Functions.Debuff("Debuff Sticky".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bMove.addActionListener((ActionEvent e) -> {
            if(!move_selection){
                SetAllButtonsNotClickableExceptOne(bMove);
                selected = null;
                move_selection = true;
                UserInputNeeded = true;
            }
            else{
                SetAllButtonsClickable();
                move_selection = false;
                UserInputNeeded = false;
            }
            Update();
        });
        bPickUpPipe.addActionListener((ActionEvent e) -> {
            Functions.RequestPart("RequestPart Pipe".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bPickUpPump.addActionListener((ActionEvent e) -> {
            Functions.RequestPart("RequestPart Pump".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bDisconnect.addActionListener((ActionEvent e) -> {
            if(!disconnect_selection){
                SetAllButtonsNotClickableExceptOne(bDisconnect);
                selected = null;
                disconnect_selection = true;
                UserInputNeeded = true;
            }
            else{
                SetAllButtonsClickable();
                disconnect_selection = false;
                UserInputNeeded = false;
            }
            Update();
        });
        bChangeFlow.addActionListener((ActionEvent e) -> {
            if(!change_flow_selection_1 && !change_flow_selection_2){
                SetAllButtonsNotClickableExceptOne(bChangeFlow);
                selected = null;
                change_flow_selection_1 = true;
                change_flow_selection_2 = false;
                UserInputNeeded = true;
            }
            else{
                SetAllButtonsClickable();
                change_flow_selection_1 = false;
                change_flow_selection_2 = false;
                UserInputNeeded = false;
            }
            Update();
        });
        bPlacePump.addActionListener((ActionEvent e) -> {
            if(!place_pump_selection){
                SetAllButtonsNotClickableExceptOne(bPlacePump);
                selected = null;
                UserInputNeeded = true;
                place_pump_selection = true;
            }
            else {
                SetAllButtonsClickable();
                UserInputNeeded = false;
                place_pump_selection = false;
            }
            Update();
        });
        bPlacePipe.addActionListener((ActionEvent e) -> {
            Functions.Place("Place Pipe".split(" "));
            Update();
            akcioPont--;
            if(akcioPont == 0) TurnusLeptetes();
        });
        bBackToMenu.addActionListener((ActionEvent e) -> {
            Purge();
            game.BackToMenu();
        });

        buttons.add(bEndRound);
        buttons.add(bRepair);
        buttons.add(bSabotage);
        buttons.add(bMakeSlippery);
        buttons.add(bMakeSticky);
        buttons.add(bMove);
        buttons.add(bPickUpPipe);
        buttons.add(bPickUpPump);
        buttons.add(bDisconnect);
        buttons.add(bChangeFlow);
        buttons.add(bPlacePump);
        buttons.add(bPlacePipe);
        buttons.add(bBackToMenu);

        button_container.add(bEndRound);
        button_container.add(bRepair);
        button_container.add(bSabotage);
        button_container.add(bMakeSlippery);
        button_container.add(bMakeSticky);
        button_container.add(bMove);
        button_container.add(bPickUpPipe);
        button_container.add(bPickUpPump);
        button_container.add(bDisconnect);
        button_container.add(bChangeFlow);
        button_container.add(bPlacePump);
        button_container.add(bPlacePipe);

        button_container.add(bBackToMenu);
        JButton version = new JButton("Version 0.1");
        version.setEnabled(false);
        button_container.add(version);
        this.add(canvas);
        this.add(button_container, BorderLayout.PAGE_END);

        canvas.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                if(place_pump_selection){
                    Mechanic m = Main.GetAsMechanic(Main.selectedPlayer);
                    if(m == null) return;
                    Pump inv = m.GetControlledPump();
                    Functions.Place("Place Pump".split(" "));
                    if(m.GetControlledPump() != inv){
                        CreateNewGraphicsObject(new Tuple<String, Object>(Main.GetNameObs(inv), inv), x, y);
                    }
                    bPlacePump.doClick();
                    akcioPont--;
                    if(akcioPont == 0) TurnusLeptetes();
                    return;
                }

                boolean found=false;
                for(var a : lista){
                    if (a.Intersect(x,y)){
                        a.setSelected(true);
                        selected=a;
                    }
                }

                for (var a: lista){
                    if (a!=selected) a.setSelected(false);
                }

                // Gomb kezelő bizbasz
                if(selected != null){
                    if(move_selection){
                        Functions.Move(("Move " + Main.GetNameObs(selected.getRef())).split(" "));
                        bMove.doClick();
                        akcioPont--;
                        if(akcioPont == 0) TurnusLeptetes();
                    }
                    else if(disconnect_selection){
                        Functions.Disconnect(("Disconnect " + Main.GetNameObs(selected.getRef())).split(" "));
                        bDisconnect.doClick();
                        akcioPont--;
                        if(akcioPont == 0) TurnusLeptetes();
                    }
                    else if(change_flow_selection_1){
                        flow_input = selected;
                        selected = null;
                        change_flow_selection_1 = false;
                        change_flow_selection_2 = true;
                    }
                    else if(change_flow_selection_2){
                        Functions.ChangeFlow(("ChangeFlow " + Main.GetNameObs(flow_input.getRef()) + " " + Main.GetNameObs(selected.getRef())).split(" "));
                        bChangeFlow.doClick();
                        akcioPont--;
                        if(akcioPont == 0) TurnusLeptetes();
                    }
                }
                Update();
            }
        });
    }

    /**
     * A TurnusLeptetes metódus felelős a játék köreinek léptetéséért és a játék végállapotának kezeléséért.
     * Ellenőrzi, hogy a játék véget ért-e a GameController osztály segítségével. Ha a játék véget ért,
     * akkor kiértékeli a gyűjtött és szivárgó vízmennyiséget, majd megjeleníti az eredményt egy üzenetablakban.
     * Az üzenetablak tartalmazza a nyertes csapatot és a gyűjtött, valamint a szivárgott vízmennyiséget.
     * Ezt követően megtisztítja a játékállapotot a Purge metódus meghívásával, majd visszatér a menübe
     * a game.BackToMenu metódus meghívásával.
     */
    private void TurnusLeptetes(){
        if(GameController.getInstance().GetEndGameInt()){
            int collected   = GameController.getInstance().getCollectedWater();
            int leaked      = GameController.getInstance().getLeakedWater();
            {
                if(collected < leaked){
                    JOptionPane.showMessageDialog(null, "A szabotőrök nyertek\nCollected water:"+Integer.toString(collected)+"\nLeaked water:"+Integer.toString(leaked), "Játék Eredmény", JOptionPane.INFORMATION_MESSAGE);
                } else if (collected > leaked)  {
                    JOptionPane.showMessageDialog(null, "A szerelők nyertek\nCollected water:"+Integer.toString(collected)+"\nLeaked water:"+Integer.toString(leaked), "Játék Eredmény", JOptionPane.INFORMATION_MESSAGE);
                } else{
                    JOptionPane.showMessageDialog(null, "Döntetlen", "Játék Eredmény", JOptionPane.INFORMATION_MESSAGE);
                }

            }
            Purge();
            game.BackToMenu();
            return;
        }
        //FIFO kezelés
        Player p = PlayerFIFO.get(0);
        PlayerFIFO.remove(0);
        PlayerFIFO.add(p);
        numOfTurnPart++;
        akcioPont = 3;
        if(numOfTurnPart == PlayerFIFO.size()){
            numOfTurnPart = 0;
            Functions.Step(new String[]{"Step"});
            Update();
        }
        Functions.SetPlayer(new String[]{"SetPlayer", Main.GetNameObs(PlayerFIFO.get(0))});
    }

    /**
     * A SetAllButtonsNotClickableExceptOne metódus felelős az összes gomb letiltásáért, kivéve egyetlen gombot.
     * Bejárja a gombok listáját, és a kivételekkel ellátott gombot kivéve minden gombot letilt a setEnabled(false) metódus hívásával.
     *
     * @param excluded Az a JButton objektum, amelyet kivételként szeretnénk megjeleníteni és engedélyezni.
     *                 Ez a gomb lesz az egyetlen engedélyezett gomb a többi letiltott gomb között.
     */

    private void SetAllButtonsNotClickableExceptOne(JButton excluded){
        for(JButton btn : buttons){
            if(btn != excluded){
                btn.setEnabled(false);
            }
        }
    }
    /**
     * A SetAllButtonsClickable metódus felelős az összes gomb engedélyezéséért.
     * Bejárja a gombok listáját és minden gombot engedélyez a setEnabled(true) metódus hívásával.
     * Ezáltal lehetővé teszi a felhasználó számára, hogy interakcióba lépjen a gombokkal és aktiválja őket.
     */

    private void SetAllButtonsClickable(){
        for(JButton btn : buttons){
            btn.setEnabled(true);
        }
    }
    /**
     * Az Initialize metódus felelős a játék inicializálásáért. Ez a metódus beállítja a kezdő értékeket, generálja és elhelyezi a játékelemeket a pályán.
     * Első lépés: végpontok generálása 2 for ciklussal
     * Második lépés: pumpák gemerálása
     * Harmadik lépés: felső csövek generálása
     * Negyedik lépés: alsó csövek generálása
     * Ötödik lépés: Pumpák ki és bemeneteinek beállítása
     * Ezután beállítjuk az elhelyezéseket a játéktéren
     * @param _team_count            A játékban résztvevő csapatok létszáma.
     * @param _source_and_drain_count A források és lefolyók száma a játékban.
     * @param _max_turns             A maximális fordulók száma a játékban.
     */
    public void Initialize(int _team_count, int _source_and_drain_count, int _max_turns){
        akcioPont = 3;
        numOfTurnPart =0;
        PlayerFIFO.clear();
        GameController.getInstance().setMaxGameTime(_max_turns);
        //végpontok generálása
        for(int i = 0; i< _source_and_drain_count; i++){
            Functions.New("New Source".split(" "));
        }
        for(int i = 0; i< _source_and_drain_count; i++){
            Functions.New("New Drain".split(" "));
        }
        //pumpák generálása
        for(int i = 0; i< _source_and_drain_count+1; i++){
            Functions.New("New Pump".split(" "));
        }
        //felső csövek generálása, szomszédsági viszonyok beállítása a gecibe
        for(int i = 0; i< _source_and_drain_count; i++){
            Functions.New("New Pipe".split(" "));
            Functions.New("New Pipe".split(" "));
            Functions.SetAttribute(("SetAttribute source"+(i+1) +" Neighbors add pipe"+(2*i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute source"+(i+1) +" Neighbors add pipe"+(2*i+2)).split(" "));

            Functions.SetAttribute(("SetAttribute pipe"+ (2*i+1) +" Neighbors add source"+(i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute pipe"+ (2*i+2) +" Neighbors add source"+(i+1)).split(" "));

            Functions.SetAttribute(("SetAttribute pump"+ (i+1) +" Neighbors add pipe"+(2*i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute pump"+(i+2) +" Neighbors add pipe"+(2*i+2)).split(" "));

            Functions.SetAttribute(("SetAttribute pipe"+ (2*i+1) +" Neighbors add pump"+(i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute pipe"+ (2*i+2) +" Neighbors add pump"+(i+2)).split(" "));
        }
        //alsó csövek generálása
        for(int i = 0; i< _source_and_drain_count; i++){
            Functions.New("New Pipe".split(" "));
            Functions.New("New Pipe".split(" "));
            Functions.SetAttribute(("SetAttribute drain"+(i+1) +" Neighbors add pipe"+(2*i+1+ _source_and_drain_count*2)).split(" "));
            Functions.SetAttribute(("SetAttribute drain"+(i+1) +" Neighbors add pipe"+(2*i+2+ _source_and_drain_count*2)).split(" "));

            Functions.SetAttribute(("SetAttribute pipe"+ ((2*i+1)+_source_and_drain_count*2) +" Neighbors add drain"+(i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute pipe"+ ((2*i+2)+_source_and_drain_count*2) +" Neighbors add drain"+(i+1)).split(" "));

            Functions.SetAttribute(("SetAttribute pump"+ (i+1) +" Neighbors add pipe"+(2*i+1+_source_and_drain_count*2)).split(" "));
            Functions.SetAttribute(("SetAttribute pump"+(i+2) +" Neighbors add pipe"+(2*i+2+_source_and_drain_count*2)).split(" "));

            Functions.SetAttribute(("SetAttribute pipe"+ ((2*i+1)+_source_and_drain_count*2) +" Neighbors add pump"+(i+1)).split(" "));
            Functions.SetAttribute(("SetAttribute pipe"+ ((2*i+2)+_source_and_drain_count*2) +" Neighbors add pump"+(i+2)).split(" "));
        }
        //pumpák src és dst beállítása
        Functions.SetAttribute(("SetAttribute pump"+ 1 +" Src pipe"+1).split(" "));
        Functions.SetAttribute(("SetAttribute pump"+ 1 +" Dst pipe"+(1 + _source_and_drain_count*2)).split(" "));
        for(int i = 1; i< _source_and_drain_count+1; i++){
            int index = i+1 +i-1;
            Functions.SetAttribute(("SetAttribute pump"+ (i+1) +" Src pipe"+index).split(" "));
            Functions.SetAttribute(("SetAttribute pump"+ (i+1) +" Dst pipe"+(index + _source_and_drain_count*2)).split(" "));
        }
        //emberek generálása
        for(int i = 0; i < _team_count; i++){
            //csávók generálása
            Functions.New("New Mechanic".split(" "));
            Functions.New("New Saboteur".split(" "));

            //csávók lerakása
            Functions.SetAttribute(("SetAttribute mechanic"+ (i+1) +" Position drain1").split(" "));
            Functions.SetAttribute(("SetAttribute saboteur"+ (i+1) +" Position source1").split(" "));
        }
        //itt lehet variálni a méretekkel meg pozíciókkal. Csinálja meg az, akinek kettő édesanyja van.
        int pos, pos_y;
        int i = 0;
        for(var a : Main.obs){
            if(i < _source_and_drain_count){
                pos = i* 200+100;
                pos_y = 100;
            }
            else if(i < _source_and_drain_count*2){
                pos = (i-_source_and_drain_count) * 200+100;
                pos_y = 600;
            }
            else {
                pos = (i - _source_and_drain_count*2)* 150+50;
                pos_y = 350;
            }
            CreateNewGraphicsObject(a, pos, pos_y);
            i++;
        }
        for(Tuple<String,Object> a : Main.obs){
            Mechanic m = Main.GetAsMechanic(a.ReturnFirst());
            if (m != null){
                PlayerFIFO.add(m);
            }
        }
        for(Tuple<String,Object> a : Main.obs){
            Saboteur m = Main.GetAsSaboteur(a.ReturnFirst());
            if (m != null){
                PlayerFIFO.add(m);
            }
        }
        Functions.SetPlayer(new String[]{"SetPlayer", Main.GetNameObs(PlayerFIFO.get(0))});
    }
    /**
     Új grafikus objektum létrehozása.
     A metódus létrehoz egy új grafikus objektumot a megadott adatok alapján. Az elem típusa
     alapján választja ki a megfelelő osztályt, majd létrehoz egy új példányt a megadott koordinátákkal.
     Végül hozzáadja az objektumot a lista gyűjteményhez és az elem nevével párosítva a connections
     gyűjteményhez.
     @param a a Tuple objektum, ami az elem nevét és referenciáját tartalmazza
     @param point_x az elem X koordinátája
     @param point_y az elem Y koordinátája
     */
    private void CreateNewGraphicsObject(Tuple<String, Object> a, int point_x, int point_y){
        String type = a.ReturnFirst().replaceAll("\\d","");
        BaseShape n = switch (type) {
            case "pipe" -> new GPipe((Pipe) a.ReturnSecond(), point_x, point_y);
            case "pump" -> new GPump((Pump) a.ReturnSecond(), point_x, point_y);
            case "drain" -> new GDrain((Drain) a.ReturnSecond(), point_x, point_y);
            case "source" -> new GSource((Source) a.ReturnSecond(), point_x, point_y);
            case "mechanic" -> new GMechanic((Mechanic) a.ReturnSecond(), point_x, point_y);
            case "saboteur" -> new GSaboteur((Saboteur) a.ReturnSecond(), point_x, point_y);
            default -> null;
        };
        if(n != null){
            if (type.equals("pipe"))
                lista.addFirst(n);
            else lista.addLast(n);
            connections.put(a.ReturnFirst(), n);
        }
    }
    /**
     Az frissítés művelet végrehajtása.
     A metódus frissíti a vásznat, majd végigmegy a Main.obs listán. Ha egy elem neve
     tartalmazza a "pipe" sztringet, és a connections gyűjtemény nem tartalmazza az elemet,
     létrehoz egy új grafikus objektumot az elemhez a megadott koordinátákkal. Végül kirajzolja
     a jelenetet.
     */
    public void Update(){
        canvas.repaint();
        for(Tuple<String, Object> a : Main.obs){
            if(a.ReturnFirst().contains("pipe") && connections.get(a.ReturnFirst()) == null){
                CreateNewGraphicsObject(a, 400, 40);
            }
        }
        DrawScene();
    }

    /**
     * A canvast újra rajzoltatja.
     */
    public void DrawScene(){
        canvas.repaint();
    }

    /**
     A törlés művelet végrehajtása.
     A metódus meghívja a Functions osztály Destroy() metódusát a null paraméterrel,
     majd törli az elemeket a listából.
     */
    public void Purge(){
        Functions.Destroy(null);
        lista.clear();
    }

    private String SelectComponent(){
        // TODO: This
        //JORDAN NEVER DID THAT MOOOoooOOOooooVe
        return null;
    }
    /**
     Felhasználói bemenet bekérése.
     A metódus null értéket állít be a kiválasztottnak, majd addig vár, amíg a kiválasztott
     értéke null marad. Visszatér a kiválasztott elem nevével.
     @return a felhasználó által kiválasztott elem neve
     */
    private String RequireInput(){

        return Main.GetNameObs(selected.getRef());
    }
    /**
     Az osztály komponensének megjelenítését végző metódus.
     A metódus meghívja az ősosztály paintComponent() metódusát, majd végrehajtja
     a saját komponens megjelenítési műveleteit.
     @param g a grafikus kontextus, amire a komponenst kirajzolja
     */
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
    }

    /**
     Kirajzolja a részleteket a grafikus felületre.
     A metódus beállítja a megadott szöveg színt és a keret méretét, majd kirajzol egy téglalapot
     a megadott x és y koordinátákkal, valamint a megadott szélességgel és magassággal.
     Ezután beállítja a betűtípusokat és kirajzolja a részleteket a grafikus felületre.
     A részletek között szerepel a játék idő, a szivárgó víz, a kiválasztott elem neve,
     valamint a gyűjtött víz mennyisége és további részletek az elem típusától függően.
     @param g a grafikus kontextus, amire a részleteket kirajzolja
     @param x a téglalap bal felső sarkának x koordinátája
     @param y a téglalap bal felső sarkának y koordinátája
     */
    private void DrawDetails(Graphics g,int x,int y){
        g.setColor(Color.BLACK);
        int width=155,height=200;
        g.drawRect(x,y,width,height);
        Font font = new Font("Arial", Font.BOLD, 12);
        Font font2 = new Font("Arial", Font.BOLD, 30);
        g.setFont(font2);
        g.drawString("Round: "+ GameController.getInstance().getGameTime(),10,670);
        g.drawString("Leaked water: "+ GameController.getInstance().getLeakedWater(),700,25);
        g.drawString( " "+Main.GetNameObs(PlayerFIFO.get(0))+": "+ akcioPont,400,25);
        g.drawString("Collected water: "+ GameController.getInstance().getCollectedWater(),20,25);

        g.setFont(font);
        int h=16;
        int i=1;
        String enter=",";
        g.drawString("Details:",x+7,y+h*i++);
        if (selected!=null) {
            g.drawString("Name:  "+Main.GetNameObs(selected.getRef()),x+7,y+h*i++);
            String name=Main.GetNameObs(selected.getRef());
            if (name == null) return;
            if (Main.GetAsField(name)!=null){

                 //Kiírja a szomszédokat
                Field f=(Field)selected.getRef();
                int SzomszedSzam=f.Neighbors.size();
                if (SzomszedSzam>0){
                    g.drawString("Neighbors: "+Main.GetNameObs(f.Neighbors.get(0))+",",x+7,y+h*i++);
                    String tmp="";
                    int count=0;
                    for (int j=1;j<SzomszedSzam;++j){
                        if (count==3){
                            g.drawString(tmp,x+7,y+h*i++);
                            count=0;
                            tmp="";
                        }
                        if (j+1!=SzomszedSzam)
                            tmp+=Main.GetNameObs(f.Neighbors.get(j))+",";
                        else tmp+=Main.GetNameObs(f.Neighbors.get(j));
                        count++;
                    }
                    if (count>0){
                        g.drawString(tmp,x+7,y+h*i++);
                    }

                }
                else{
                    g.drawString("Neighbors: "+"-",x+7,y+h*i++);
                }

            }
            if (name.contains("pipe")){
                Pipe csel=(Pipe)selected.getRef();
                g.drawString("HasWater: "+csel.HasWater,x+7,y+h*i++);
                if (csel.IsSticky() ){
                    g.drawString("Debuff: "+ "Sticky",x+7,y+h*i++);
                }
                else if (csel.IsSlippery()){
                    g.drawString("Debuff: "+ "Slippery",x+7,y+h*i++);
                }
                else{
                    g.drawString("Debuff: "+ "none",x+7,y+h*i++);
                }
                g.drawString("DebuffTimer: "+ csel.DebuffTimer,x+7,y+h*i++);
                g.drawString("TicksUntilBreakable: "+csel.TicksUntilBreakable,x+7,y+h*i++);
            }
            else if (name.contains("pump")){
                Pump csel=(Pump)selected.getRef();
                g.drawString("Src: "+ Main.GetNameObs(csel.GetSrc()),x+7,y+h*i++);
                g.drawString("Dst: "+ Main.GetNameObs(csel.GetDst()),x+7,y+h*i++);
                g.drawString("Buffer: "+ csel.Buffer,x+7,y+h*i++);
                g.drawString("BufferCap: "+ csel.BufferCap,x+7,y+h*i++);
                g.drawString("TicksUntilBreak: "+ csel.TicksUntilBreak,x+7,y+h*i++);

            }
            else if (name.contains("drain")){
                Drain csel=(Drain)selected.getRef();
                g.drawString("PipeCounter: "+ csel.PipeCounter,x+7,y+h*i++);
            }
            else if (name.contains("mechanic") || name.contains("saboteur")){
                Player player=(Player)selected.getRef();
                g.drawString("Position: "+ Main.GetNameObs(player.GetPosition()),x+7,y+h*i++);
                g.drawString("Stuck: "+ player.Stuck,x+7,y+h*i++);
                if (name.contains("mechanic")){
                    Mechanic mech=(Mechanic) selected.getRef();
                    g.drawString("ControlledPump: "+Main.GetNameObs( mech.GetControlledPump()),x+7,y+h*i++);
                    g.drawString("ControlledPipe: "+ Main.GetNameObs(mech.GetControlledPipe()),x+7,y+h*i++);
                    g.drawString("NumberOfEndpoints: "+ mech.NumberOfEndpoints,x+7,y+h*i++);
                }
            }

        }
    }
    /**
     Kirajzolja a felhasználói bemenetet a grafikus felületre.
     A metódus frissíti a vásznat, majd ellenőrzi, hogy szükség van-e felhasználói bemenetre.
     Ha igen, beállítja a betűtípust, a szöveg színét és kiszámítja a szöveg középre igazításához szükséges rést.
     Ezután kirajzolja a felhasználói bemenetet a grafikus felületre. A felhasználói bemenet szövege a
     "!KATTINTS RÁ EGY ELEMRE!" sztring. A szöveg pozíciója a vászon közepén lesz elhelyezve.
     @param g a grafikus kontextus, amire a felhasználói bemenetet kirajzolja
     */
    private void DrawUserInput(Graphics g){
        canvas.repaint();
        if (UserInputNeeded==true){
            Font font = new Font("Arial", Font.BOLD, 40);
            g.setFont(font);
            g.setColor(Color.RED);
            int gap=g.getFontMetrics().stringWidth("!KATTINTS RÁ EGY ELEMRE!")/2;
            g.drawString("!KATTINTS RÁ EGY ELEMRE!",1024/2-gap,80);
        }
    }
}