package UrlChangeCheckService;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hash {
    public static String getCryptoHash(String input) {
//        try {
//
//            MessageDigest msgDigest = MessageDigest.getInstance("SHA-512");
//            byte[] inputDigest = msgDigest.digest(input.getBytes());
//
//
//            BigInteger inputDigestBigInt = new BigInteger(1, inputDigest);
//
//            // Convert the input digest into hex value
//            String hashtext = inputDigestBigInt.toString(16);
//
//            while (hashtext.length() < 32) {
//                hashtext = "0" + hashtext;
//            }
//            return hashtext;
//        }
//        catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
        try{
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
        messageDigest.update(input.getBytes());
        String encryptedString = new String(messageDigest.digest());
        return encryptedString;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

}
}
