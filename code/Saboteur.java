
public class Saboteur extends Player {

    /**
     * Létrehozza az objektumot.
     */
    public Saboteur(){
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("saboteur" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("saboteur" + i, this));
        Main.saboteurs.put(this, this);
    }

    /**
     * Megkísérli az általa elfoglalt objektum
     * elrontását.
     */

    public void MakeSlippery(){
        Position.MakeSlippery();
    }

}
