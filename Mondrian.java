package CoolMath;

import java.util.ArrayList;

public class Mondrian {
    CustomDimension canvas;
    ArrayList<CustomDimension> rects = new ArrayList<>();

    public Mondrian(){
        this(3);
    }

    public Mondrian(int side){
        this.canvas = new CustomDimension(side);
    }

    public CustomDimension getCanvas() {
        return canvas;
    }

    public ArrayList<CustomDimension> getRects() {
        return rects;
    }
}
