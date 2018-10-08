package CoolMath;

import CoolMath.GeneralMath;

public class Ciphers {
    private static final String STANDARD_ALPHA_KEY = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final char LOWERCASE = 'a', UPPERCASE = 'A';

    //--Vigenére Based Ciphers--//

    public static String vigenere(String message, String password){
        return vigenere(message, password, false);
    }
    public static String vigenere(String message, String password, boolean decrypt) {
        /*StringBuilder ret = new StringBuilder();
        password = password.toLowerCase();
        int passwordCur, mesCur, val, charUse, offset = 0;
        for(int i = 0; i<message.length(); i++){
            mesCur = message.charAt(i);
            if(isLower((char)mesCur)) charUse = 'a';
            else if(isUpper((char)mesCur)) charUse = 'A';
            else {
                ret.append((char)mesCur);
                offset++;
                continue;
            }
            mesCur -= charUse;
            passwordCur = (int)(password.charAt((i - offset) % password.length())) - 97;
            val = ((decrypt) ? (mesCur - passwordCur) : (mesCur + passwordCur)) + 26;
            ret.append((char)((val) % 26 + charUse));
        }
        return ret.toString();*/
        return vigenere(message, password, "", decrypt);
    }
    public static String vigenere(String message, String password, String keyed, boolean decrypt){
        password = password.toLowerCase();
        String key = getAlphabetKey(keyed);
        StringBuilder ret = new StringBuilder();
        int passCur, mesCur, val, offset = 0;
        char charUse;

        for(int i = 0; i<message.length(); i++){
            mesCur = message.charAt(i);

            if(isLower((char)mesCur)) charUse = LOWERCASE;
            else if(isUpper((char)mesCur)) charUse = UPPERCASE;
            else {
                ret.append((char)mesCur);
                offset--;
                continue;
            }

            //Will be a number 0-25
            mesCur  = getKeyedIndex(key, charToUpper((char) (mesCur)));
            passCur = getKeyedIndex(key, charToUpper(password.charAt((i + offset) % password.length())));

            //Gets position
            val     = (((decrypt) ? (mesCur - passCur) : (mesCur + passCur)) + 26) % 26;
            val     = getKeyedChar(key, val);

            ret.append((char)((isUpper(charUse)) ? (val) : (charToLower((char)val))));
        }
        return ret.toString();
    }

    //--Affine Based Ciphers--//

    public static String caesar(String message){
        return caesar(message, 3);
    }
    public static String caesar(String message, char rot) {
        return caesar(message, ((rot - 'a' < 0) ? (rot - 'A') : (rot - 'a')));
    }
    public static String rot13(String message){
        return caesar(message, 13);
    }
    public static String caesar(String message, int rot) {
        /*StringBuilder ret = new StringBuilder();
        int charUse;
        for(char c : message.toCharArray()){
            if(isLower(c)) charUse = 'a';
            else if(isUpper(c)) charUse = 'A';
            else {
                ret.append(c);
                continue;
            }
            ret.append((char)(((c-charUse) + rot)%26+charUse));
        }*/
        return affine(message, rot, 1, false);
    }

    public static String atbash(String message) {
        StringBuilder ret = new StringBuilder();
        int charUse;
        for(char c: message.toCharArray()){
            if(isLower(c)) charUse = LOWERCASE;
            else if(isUpper(c)) charUse = UPPERCASE;
            else {
                ret.append(c);
                continue;
            }
            ret.append((char)(Math.abs((c - charUse) - 25) % 26 + charUse));
        }
        return ret.toString();
    }
    public static String affbash(String message){
        return affine(message, 25,25);
    }

    public static String affine(String message, int rot, int coprime){
        return affine(message, rot, coprime, false);
    }
    public static String affine(String message, char rot, int coprime) {
        return affine(message, ((rot - 'a' < 0) ? (rot - 'A') : (rot - 'a')), coprime, false);
    }
    public static String affine(String message, char rot, int coprime, boolean decrypt) {
        return affine(message, ((rot - 'a' < 0) ? (rot - 'A') : (rot - 'a')), coprime, decrypt);
    }
    public static String affine(String message, int rot, int coprime, boolean decrypt) {
        if(GeneralMath.gcd(coprime,26) != 1){
            return "Invalid Coprime given...";
        }
        StringBuilder ret = new StringBuilder();
        int charUse;
        for(char c: message.toCharArray()){
            if(isLower(c)) charUse = LOWERCASE;
            else if(isUpper(c)) charUse = UPPERCASE;
            else {
                ret.append(c);
                continue;
            }
            ret.append((char)(((decrypt) ? (((c-charUse)-rot)/coprime) : (coprime * (c-charUse) + rot)) % 26 + charUse));
        }
        return ret.toString();
    }

    //--Helper Functions--//

    private static boolean isUpper(char c) {
        return (c >= 'A' && c <= 'Z');
    }
    private static boolean isLower(char c) {
        return (c >= 'a' && c <= 'z');
    }
    private static boolean isLetter(char c){
        return (isUpper(c) && isLower(c));
    }
    private static char charToUpper(char c){
        if(isUpper(c)) return c;
        return (char)(c - (LOWERCASE - UPPERCASE));
    }
    private static char charToLower(char c){
        if(isLower(c)) return c;
        return (char)(c + (LOWERCASE - UPPERCASE));
    }
    private static boolean sbContains(StringBuilder sb, CharSequence c){
        return sb.indexOf(c.toString()) > -1;
    }

    //--Keyed Functions--//

    private static String getAlphabetKey(String keyed){
        keyed = keyed.toUpperCase();
        if(keyed.equals("")){
            return STANDARD_ALPHA_KEY;
        }
        StringBuilder unique = new StringBuilder(), alpha = new StringBuilder().append(STANDARD_ALPHA_KEY);
        String curChar;
        for(char c: keyed.toCharArray()){
            curChar = ""+c;
            if(!(unique.indexOf(curChar) > -1)){
                unique.append(c);
            }
            if(sbContains(alpha,curChar)){
                alpha.deleteCharAt(alpha.indexOf(curChar));
            }
        }
        return unique.append(alpha.toString()).toString();
    }
    private static int getKeyedIndex(String keyed, char c){
        return keyed.indexOf(c);
    }
    private static char getKeyedChar(String keyed, int i){
        return keyed.charAt(i);
    }
}