package CoolMath;

public class Collatz {
    public Collatz(){}

    /*public static void main(String[] args){
        Scanner kb = new Scanner(System.in);
        System.out.println("Enter an number:");
        BigInteger check = new BigInteger(kb.next());
        if(check.longValue() == 2){
            System.out.println("It took 1 step to reach 1 from 2");
            System.exit(0);
        }
        BigInteger current = check;
        BigInteger steps = new BigInteger("0");
        int trailing;

        if(current.mod(new BigInteger("2")).equals(new BigInteger("0"))){
            trailing = Integer.numberOfTrailingZeros(current.intValue());
            current = current.divide(new BigInteger(""+Math.pow(2,trailing)));
            steps = steps.add(new BigInteger(""+trailing));
        }
        while (!current.equals(new BigInteger("1"))){
            current = current.multiply(new BigInteger("3")).add(new BigInteger("1"));
            trailing = Integer.numberOfTrailingZeros(current.intValue());
            int div = (int) Math.pow(2,trailing);
            current = current.divide(new BigInteger(""+div));
            trailing++;
            steps = steps.add(new BigInteger(""+trailing));
        }
        System.out.println("It took "+steps+" steps to reach 1 from "+check);
    }*/

    public int findStepsFrom(int given){
        given = Math.abs(given);
        switch(given){
            case 0:
            case 1: return 0;
            case 2: return 1;
            default: break;
        }
        int steps = 0, trailing;

        if(given % 2 == 0){
            trailing = Integer.numberOfTrailingZeros(given);
            given /= (int) Math.pow(2, trailing);
            steps += trailing;
        }

        while(given != 1){
            given = (given * 3) + 1;
            trailing = Integer.numberOfTrailingZeros(given);
            given /= (int) Math.pow(2, trailing);
            trailing++;
            steps += trailing;
        }

        return steps;
    }
}
