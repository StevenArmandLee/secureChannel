/*
 * Name: Steven Lee
 * Student ID: 4643483
 * Source: http://esus.com/encryptingdecrypting-using-rc4/
 * 
 */
package secureChannel;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.crypto.spec.*;
import java.security.*;
import javax.crypto.*;
  
public class RC4
{
   private static String algorithm = "RC4";
  
  
   public static String encrypt(String toEncrypt, String key) throws Exception {
      // create a binary key from the argument key (seed)
      SecureRandom sr = new SecureRandom(key.getBytes("ISO-8859-1"));
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(sr);
      SecretKey sk = kg.generateKey();
  
      // create an instance of cipher
      Cipher cipher = Cipher.getInstance(algorithm);
  
      // initialize the cipher with the key
      cipher.init(Cipher.ENCRYPT_MODE, sk);
  
      // enctypt!
      byte[] encrypted = cipher.doFinal(toEncrypt.getBytes("ISO-8859-1"));
  
      return new String(encrypted,"ISO-8859-1");
   }
  
   public static String decrypt(String cipherText, String key) throws Exception {
	   byte[] toDecrypt = cipherText.getBytes("ISO-8859-1");
      // create a binary key from the argument key (seed)
      SecureRandom sr = new SecureRandom(key.getBytes("ISO-8859-1"));
      KeyGenerator kg = KeyGenerator.getInstance(algorithm);
      kg.init(sr);
      SecretKey sk = kg.generateKey();
  
      // do the decryption with that key
      Cipher cipher = Cipher.getInstance(algorithm);
      cipher.init(Cipher.DECRYPT_MODE, sk);
      byte[] decrypted = cipher.doFinal(toDecrypt);
  
      return new String(decrypted,"ISO-8859-1");
   }
}

