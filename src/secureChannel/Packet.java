package secureChannel;

import java.io.Serializable;

public class Packet implements Serializable{
	String message;
	String hashOfMessage;
	
	
	Packet()
	{
		
	}
	
	Packet(String message, String hashOfMessage)
	{
		this.message = message;
		this.hashOfMessage = hashOfMessage;
	}

	public String getMessage() {
		return message;
	}

	public String getHashOfMessage() {
		return hashOfMessage;
	}
	
	

}
