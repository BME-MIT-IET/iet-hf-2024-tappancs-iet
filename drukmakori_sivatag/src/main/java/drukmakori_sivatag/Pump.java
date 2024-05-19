package drukmakori_sivatag;

import java.util.Random;

//Kész
public class Pump extends Field {
    Field src,dst;


    int Buffer = 0,BufferCap = 1;
    int TicksUntilBreak = new Random().nextInt(10);

    /**
     * Getter/Setter for testing purposes
     */

    public int getBuffer() {
        return Buffer;
    }

    /**
     * A pumpa step függvénye, mely minden
     * Tick-ben meghívódik a Timer által. A
     * pumpa - amennyiben nincs elromolva -
     * kiszivattyúzza a vizet a bemeneti csőből, és
     * átpumpálja a kimeneti csőbe. Amennyiben
     * a pumpa buffere nincs megtelve, a bufferbe
     * helyezi kipumpálás helyett.
     */
    @Override
    public void Step(){
        if (IsBroken) return;

        TicksUntilBreak--;
        if(dst!=null && Buffer==BufferCap){
            dst.Fill();
            Buffer=Buffer-1;
        }
        if(src!=null && src.PullWater()){
            if (BufferCap>Buffer){
                Buffer++;
            }

        }

        if(TicksUntilBreak==0) {
            SetIsBroken(true);
        }

    }
    @Override
    public boolean DisconnectPipe(Field f){
        if(f == dst) dst = null;
        return super.DisconnectPipe(f);
    }
    /**
     * Beállítja a buffer értékét.
     */
    public void SetBuffer(int val){
        Buffer=val;
    }
    /**
     * Visszaadja a BufferCap értékét.
     */
    public void SetBufferCap(int val){
        BufferCap=val;
    }

    /**
     * Megváltoztatja a ki és bemeneti csöveket.
     * Ha a bejövő paraméter ugyan az mint a
     * jelenlegi, nem változik.
     * @param src a változtatás után a pumpa kimenete
     * @param dst a változtatás után a pumpa bemenete
     */
    @Override
    public void Change(Field src, Field dst){
        if (IsNeighbor(src) && IsNeighbor(dst)){
            this.src=src;
            this.dst=dst;
        }
    }

    @Override
    public void Fix(){
        IsBroken=false;
        TicksUntilBreak=new Random().nextInt(10);
    }
    /**
     * Beállítja az src értékét.
     * @param src a bemenetének lesz az értéke
     */
    public void SetSrc(Field src){
        this.src=src;
    }

    /**
     * Beállítja az src értékét.
     * @param dst a kimenetelének lesz az értéke
     */
    public void SetDst(Field dst){
        this.dst=dst;
    }

    /**
     * Visszaadja az src értékét.
     * @return a bemeneteli elem
     */
    public Field GetSrc(){
        return src;
    }

    /**
     * Visszaadja az dst értékét.
     * @return kimeneteli elem
     */
    public Field GetDst(){
        return dst;
    }

    /**
     * Létrehozza az objektumot.
     */
    public Pump(){
        int i = 1;
        boolean found = true;
        while(found) {
            found = false;
            for (Tuple<String, Object> a : Main.obs)
                if (a.ReturnFirst().equals("pump" + i)) {
                    i++;
                    found = true;
                    break;
                }
        }
        Main.obs.add(new Tuple<String, Object>("pump" + i, this));
        Main.fields.put(this, this);
        Main.steppables.put(this, this);
    }
    public void Details(){
        super.Details();
        String indent="\t\t";
        Main.WriteIntoFilesAndConsole(indent+"Src: "+Main.GetNameObs(src),false);
        Main.WriteIntoFilesAndConsole(indent+"Dst: "+Main.GetNameObs(dst),false);
        Main.WriteIntoFilesAndConsole(indent+"Buffer: "+Buffer,false);
        Main.WriteIntoFilesAndConsole(indent+"BufferCap: "+BufferCap,false);
        Main.WriteIntoFilesAndConsole(indent+"TicksUntilBreakable: "+TicksUntilBreak,false);


    }
}