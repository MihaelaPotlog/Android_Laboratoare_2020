package Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashCalculator {
    public static String getHash(String text){
        MessageDigest messageDigest;
        try{
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(text.getBytes());
            return  new String(messageDigest.digest());}
        catch(NoSuchAlgorithmException excep){
            System.out.println(excep);
        }
        return null;
    }
}
