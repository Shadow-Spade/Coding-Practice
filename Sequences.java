package CoolMath;

import java.util.ArrayList;

public class Sequences {
    public enum SeriesType {
        PRIME,
        FIBONACCI,
        ULAM,
        BAUM,
        LUCKY
    }

    private static ArrayList<Integer> storage = new ArrayList<>();
    public Sequences(){}

    public static ArrayList<Integer> getSequenceTo(SeriesType type, int max) {
        for(int i=1; i<max; i++){
            if(isCheckType(type,i)){
                storage.add(i);
            }
        }
        return storage;
    }

    private static boolean isCheckType(SeriesType type,int index) {
        switch (type){
            case PRIME:     return isPrime(index);
            case FIBONACCI: return isFibonacci(index);
            case ULAM:      return isUlam(index);
            case BAUM:      return isBaumSweet(index);
            case LUCKY:     return isLucky(index);
            default:{
                System.out.println("Sequence error");
                return false;
            }
        }
    }

    private static boolean isPrime(int index) {
        //One's not prime
        if(index == 1) {
            return false;
        }

        double chk = Math.sqrt(index);
        //Check if divisible by any prime
        for(int z : storage) {
            //This will exponentially get longer the more numbers added
            if(index % z == 0) {
                return false;
            }
            //Reduces checking size greatly
            if(z > chk) {
                break;
            }
        }
        return true;
    }

    private static boolean isFibonacci(int index) {
        return (index == storage.get(storage.size()-1)+storage.get(storage.size()-2));
    }

    private static boolean isUlam(int index) {
        switch(index){
            case 1:
            case 2:
            case 3:
            case 47:
            case 69: return true;
            default: break;
        }
        //Apparently a good check
        if(Math.cos(2.5714474995*index)>=0){
            return false;
        }

        int distinct = 0;
        for(int z : storage){
            if(binarySearch(storage, index - z )){
                if(z*2 != index){
                    distinct++;
                }
            }
            if(distinct>2){
                return false;
            }
        }
        return (distinct != 0);
    }

    private static boolean isBaumSweet(int index){
        String search = Integer.toBinaryString(index);
        int count = 0;
        for(char z : search.toCharArray()){
            if(z == '0'){
                count++;
            }
            else{
                if(count%2==1){
                    return false;
                }
                else {
                    count=0;
                }
            }
        }
        return count%2==0;
    }

    private static boolean isLucky(int index){
        return binarySearch(storage,index);
    }

    private static void sieveLuckyListInclusive(){
        //--This is used if you initialised the list with even numbers--//
        //Every time this increments another lucky number has been found
        for(int index = 0; index<storage.size(); index++){
            int z = storage.get(index);
            //Removes every Zth number from the list
            for(int w = 0; w<storage.size(); w += (z==1) ? z : z-1){
                if(w!=0){
                    storage.remove(w);
                }
            }
        }
    }

    private static void sieveLuckyListExclusive(){
        //--This is used if you initialised the list without even numbers--//
        //Every time this increments another lucky number has been found
        for(int index = 1; index<storage.size(); index++){
            int z = storage.get(index);
            //Removes every Zth number from the list
            for(int w = z-1; w<storage.size(); w += z-1){
                storage.remove(w);
            }
        }
    }

    private static boolean binarySearch(ArrayList<Integer> data, int find) {
        int start=0, end=data.size()-1;
        while(start <= end) {
            int check = (start + end) / 2;
            if (data.get(check) == find) {
                return true;
            }
            else if (data.get(check) > find) {
                end = check-1;
            }
            else{
                start = check+1;
            }
        }
        return false;
    }

}
