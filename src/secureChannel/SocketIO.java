package secureChannel;

import java.io.IOException;
import java.io.ObjectOutputStream;

public class SocketIO {
	
	CryptoTools cryptoTools = new CryptoTools();

	public void sendPacket(String message, String key, ObjectOutputStream responses)
	{
		Packet packet = new Packet(cryptoTools.decrypt(message, key), cryptoTools.SHA1(message));
		try {
			responses.writeObject(packet);
			responses.flush();
			responses.reset();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
