package CoolMath;

public class Fraction {
    private final int numerator, denominator;

    public Fraction(int num){
        this(num, 1);
    }

    public Fraction(double var){
        //TODO
        this.numerator = 0;
        this.denominator = 0;
    }

    public Fraction(int num, int den){
        if(den == 0){
            throw new ArithmeticException("Cannot Devide by Zero");
        }
        if(den < 0){
            num = -num;
            den = -den;
        }

        final int d = GeneralMath.gcd(num, den);
        if(d > 1){
            num /= d;
            den /= d;
        }

        this.numerator = num;
        this.denominator = den;
    }

    public Fraction add(int i){
        return new Fraction(numerator + (i * denominator), denominator);
    }

    public Fraction subtract(int i){
        return new Fraction(numerator - (i * denominator), denominator);
    }

    public Fraction multiply(int i){
        return new Fraction(numerator * i,denominator);
    }

    public Fraction abs(){
        return ((this.numerator >= 0) ? this : negate());
    }

    public Fraction negate(){
        return new Fraction(-numerator,denominator);
    }

    public Fraction reciprocal(){
        return new Fraction(denominator, numerator);
    }

    public double getDouble(){
        return (double) numerator / (double) denominator;
    }

    public int getInt(){
        return (int) getDouble();
    }

    public long getLong(){
        return (long) getDouble();
    }

    public float getFloat(){
        return (float) getDouble();
    }

    public double getAsPercentage(){
        return getDouble() * 100;
    }

    public int getNumerator() {
        return numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    @Override
    public String toString() {
        String str = numerator+"/"+denominator;
        if(denominator == 1){
            str = Integer.toString(numerator);
        } else if(numerator == 0) {
            str = "0";
        }
        return str;
    }
}
