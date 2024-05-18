package drukmakori_sivatag;

public class Tuple<X, Y> {
    public X x;
    public Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }
    X ReturnFirst(){
        return x;
    }
    Y ReturnSecond(){
        return y;
    }
}