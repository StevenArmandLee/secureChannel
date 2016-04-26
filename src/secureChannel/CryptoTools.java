package secureChannel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CryptoTools {
	RC4 rc4 = new RC4();
	
	public String encrypt(String message, String key)
	{
		StringBuffer stringBuffer = new StringBuffer();
		for(int i=0; i<rc4.crypt(key, message).length;i++)
		{
			stringBuffer.append(rc4.crypt(key, message)[i]);
		}
	
		return stringBuffer.toString();
	}
	public String decrypt(String message, String key)
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
	
	public boolean authenticateMessage(String message, String hashOfMessage, String key)
	{
		boolean decision = false;
		if(SHA1(decrypt(message, key)).equals(hashOfMessage))
		{
			decision= true;
		}
		
		
		return decision;
	}
	

}
