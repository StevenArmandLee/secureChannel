package secureChannel;

import java.util.Random;

public class Nonce {
	final static int MAX_NONCE_LENGTH = 0;
	final static char[] CHARACTERS= {'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z',
		'0','1','2','3','4','5','6','7','8','9','!','@','#','$','%','^','&','*','(',')','{','}','[',']',';'};
	static Random r = new Random();
	
	public static String getNonce()
	{
		StringBuffer stringBuffer = new StringBuffer();
		
		
		for(int i=0; i<MAX_NONCE_LENGTH;i++)
		{
			stringBuffer.append(CHARACTERS[r.nextInt(CHARACTERS.length)]);
		}
		
		return stringBuffer.toString();
		
	}
	
}
