package secureChannel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoTools {
	static RC4 rc4 = new RC4();
	
	public static String encrypt(String message, String key)
	{
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0; i<rc4.crypt(key, message).length;i++)
		{
			stringBuffer.append(rc4.crypt(key, message)[i]);
		}
	
		return stringBuffer.toString();
	}
	public static String decrypt(String message, String key)
	{
	
		return encrypt(message,key);
	}
	
	
	public static String SHA1(String input)
	{
		MessageDigest mDigest;
		StringBuffer stringBuffer = new StringBuffer();
		try {
			mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			for(int i = 0; i < result.length; i++)
			{
				stringBuffer.append(Integer.toString((result[i] & 0xff)+0x100,16).substring(1));
				
			}
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stringBuffer.toString();
	}
	
	public static String encryptMessage(String message, String key)
	{
		String hash = SHA1((key+message+key));
		String cipherText = encrypt(message+hash,key);
		
		return cipherText;
		
	}
	
	public static String getMessage(String message, String key)
	{
		return decrypt(message,key).substring(0, message.length()-40);
	}
	
	public static String getHash (String message, String key)
	{
		return decrypt(message,key).substring(message.length()-40, message.length());
		//return message.substring(message.length()-40, message.length());
	}
	
	public boolean authenticateMessage(String message, String key)
	{
		boolean decision = false;
		String hashOfMessage = getHash(message,key);
		String actualMessage = getMessage(message, key);
		if(SHA1((key+actualMessage+key)).equals(hashOfMessage))
		{
			decision= true;
		}
		
		
		return decision;
	}
	

}
