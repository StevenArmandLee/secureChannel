package secureChannel;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class secureChannel {

	public static String SHA1(String input) throws NoSuchAlgorithmException
	{
		MessageDigest mDigest = MessageDigest.getInstance("SHA1");
		byte[] result = mDigest.digest(input.getBytes());
		StringBuffer stringBuffer = new StringBuffer();
		
		for(int i = 0; i < result.length; i++)
		{
			stringBuffer.append(Integer.toString((result[i] & 0xff)+0x100,16).substring(1));
			
			
			
		}
		return stringBuffer.toString();
	}
}
