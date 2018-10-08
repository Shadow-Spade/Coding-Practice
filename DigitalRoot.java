package CoolMath;

public class DigitalRoot {
    //Default base is 10
    private int base;

    public DigitalRoot(){
        this(10);
    }

    public DigitalRoot(int base){
        this.base = base;
    }

    private int stepDigitalRoot(int num, int len){
        int step = 0;
        for(int i = 0; i < len; i++){
            step += (int) Math.floor(num/Math.pow(base,i))%base;
        }
        //returns the addition of all the current numbers
        return step;
    }

    public int getDigitalRoot(int num){
        //makes sure number is not negative
        num = Math.abs(num);
        for(int len = GeneralMath.getIntLength(num,base); len > 1; len = GeneralMath.getIntLength(num, base)){
            num = stepDigitalRoot(num, len);
        }
        return num;
    }
}