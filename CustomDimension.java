package CoolMath;

public class CustomDimension {
    private int width, height;

    public CustomDimension(){
        this(0,0);
    }

    public CustomDimension(int side){
        this(side,side);
    }

    public CustomDimension(int width, int height){
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getArea(){
        return height * width;
    }

    public boolean isSimilarTo(CustomDimension cd){
        return (cd.getHeight() == width && cd.getWidth() == height);
    }

    @Override
    public String toString() {
        return "[" + width + " x " + height + "]";
    }
}
