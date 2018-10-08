package CoolMath;

public final class GeneralMath {
    private GeneralMath(){}

    public static double logx(double num, double base){
        return Math.log(num) / Math.log(base);
    }

    public static int getIntLength(int num, int base){
        //Log of 0 is undefined
        if(num == 0) return 1;
        //A clever way to return the number of digits in a number
        int dig = (int) Math.ceil(logx(num,base));
        //If base 10 and num 10 then add 1 to length cause exact numbers are not the length
        if(num == (int) Math.pow(base,dig)){
            dig++;
        }
        return dig;
    }

    public static long upArrow(int a, long b, int n){
        if(n == 1){
            return (long) Math.pow(a, b);
        }
        else if(n >= 1 && b == 0){
            return 1;
        }
        else {
            return upArrow(a, upArrow(a, b-1, n), n-1);
        }
    }

    public static int getFasterDigitalRoot(int num){
        //This is why you always try to look up better solutions online...
        return (int) (num - 9*(Math.floor((num-1)/9)));
    }

    public static double sec(double a){
        return 1 / Math.cos(a);
    }

    public static double csc(double a){
        return 1 / Math.sin(a);
    }

    public static double cot(double a){
        return Math.cos(a) / Math.sin(a);
    }

    public static int gcd(int a, int b){
        if(a == 0 || b == 0){
            return Math.abs(a + b);
        }
        if(a < 0){
            a = -a;
        }
        if(b < 0){
            b = -b;
        }

        final int aTwos = Integer.numberOfTrailingZeros(a);
        final int bTwos = Integer.numberOfTrailingZeros(b);
        a >>= aTwos;
        b >>= bTwos;
        final int shift = Math.min(aTwos, bTwos);

        while (a != b) {
            final int delta = a % b;
            b = Math.min(a, b);
            a = Math.abs(delta);
            a >>= Integer.numberOfTrailingZeros(a);
        }
        // Recover the common power of 2.
        return a << shift;
    }
}
