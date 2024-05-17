//Kesz
public class Source extends Field{
    public Source(){
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("source" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("source" + i, this));
        Main.fields.put(this, this);
        Main.steppables.put(this, this);
    }

    /**
     * A forrás step függvénye.
     * Minden Tick esetén meghívásra kerül a Timer által.
     * A forráshoz kötött csövek megtelnek vízzel.
     *
     */
    @Override
    public void Step(){
        for (Field f : Neighbors){
            f.Fill();
        }
    }

}
