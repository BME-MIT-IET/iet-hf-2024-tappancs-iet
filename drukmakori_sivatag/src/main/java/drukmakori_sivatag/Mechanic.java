package drukmakori_sivatag;

public class Mechanic extends Player {

    Pump ControlledPump = null;
    Pipe ControlledPipe = null;
    int NumberOfEndpoints = 0;
    /**
     * Létrehozza az objektumot.
     */
    public Mechanic(){
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("mechanic" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("mechanic" + i, this));
        Main.mechanics.put(this, this);
    }

    /**
     * Pumpát csatlakoztat arra az elmere ,ahol
     * éppen tartózkodik.
     */
    public void PlacePump(){
        if(ControlledPump == null) {
            return;
        }
        if(Position.PlacePump(ControlledPump)) {
            MoveTo(ControlledPump);

            ControlledPump = null;
        }
    }

    /**
     * Csövet csatlakoztat az elemre amelyen
     * tartózkodik.
     */
    public void PlacePipe(){
        if(ControlledPipe == null){
            return;
        }
        if(Position.ConnectPipe(ControlledPipe)) {
            NumberOfEndpoints--;
            if(NumberOfEndpoints == 0) ControlledPipe = null;
        }
    }


    /**
     * Csövet vesz fel a mezőről.
     */
    public boolean PickUpPipe(){
        if(ControlledPipe == null){
            ControlledPipe = (Pipe)Position.RequestPipe();
            NumberOfEndpoints = 1;
            if(ControlledPipe != null)
                return true;
        }
        return false;
    }
    public boolean PickUpPipe(Pipe src){
        if(ControlledPipe == null){
            if(Position.DisconnectPipe(src)) {
                ControlledPipe = src;
                src.Neighbors.remove(Position);
                NumberOfEndpoints = 1;
                return true;
            }
        }
        else if(src == ControlledPipe){
            NumberOfEndpoints = 2;
            src.Neighbors.remove(Position);
            return true;
        }
        return false;
    }
    /**
     * Pumpát vesz fel a mezőről.
     */
    public boolean PickUpPump(){
        if(ControlledPump == null){
            ControlledPump = (Pump)Position.RequestPump();
            if(ControlledPump != null)
                return true;
        }
        return false;
    }

    /**
     * Megjavítja az aktuális pozícióján lévő
     * csövet.
     */
    public void Fix(){
        Position.Fix();
    }

    /**
     * Visszaadja a ControlledPump értékét.
     * @return  a kontrolláltpumpa
     */
    public Pump GetControlledPump(){
        return ControlledPump;
    }

    /**
     * Beállítja a ControlledPump értékét.
     * @param p a beállítandó pumpa
     */
    public void SetControlledPump(Pump p) {
        ControlledPump = p;
   }

    /**
     * Visszaadja a ControlledPipe értékét.
     * @return a legenerált pipe
     */
    public Pipe GetControlledPipe(){
        return ControlledPipe;
   }

    /**
     * Beállítja a ControlledPipe értékét.
     * @param p a legenerált pipe
     */
    public void SetControlledPipe(Pipe p){
        ControlledPipe = p;
   }

    public void setNumberOfEndpoints(int number){
        NumberOfEndpoints = number;
    }
    public void Details(){
        super.Details();
        String indent="\t\t";
        Main.WriteIntoFilesAndConsole(indent+"NumberOfEndpoints: "+NumberOfEndpoints,false);
        Main.WriteIntoFilesAndConsole(indent+"ControlledPump: "+Main.GetNameObs(ControlledPump),false);
        Main.WriteIntoFilesAndConsole(indent+"ControlledPipe: "+Main.GetNameObs(ControlledPipe),false);
    }

}
