package secureChannel;

import java.io.Serializable;

public class Packet implements Serializable{
	String cipherText;
	
	Packet(String cipherText)
	{
		this.cipherText = cipherText;
	}

	public String getMessage() {
		return cipherText;
	}


	
	

}
