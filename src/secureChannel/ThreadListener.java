package secureChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.NoSuchAlgorithmException;

public class ThreadListener implements Runnable{
	ObjectInputStream responseStream;
	private Thread thread;
	private String key;
	CryptoTools cryptoTools = new CryptoTools();
	boolean running = true;
	
	ThreadListener(ObjectInputStream responseStream, String key)
	{
		this.responseStream = responseStream;
		this.key=key;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		 
		while(running)
		{
			try {
				
				Packet packet = (Packet)responseStream.readObject();
				if(cryptoTools.authenticateMessage(packet.getMessage(), packet.getHashOfMessage(), key))
				{
					System.out.println(cryptoTools.decrypt(packet.getMessage(), key));
					
				}
				else
				{
					System.out.println("decryption error");
				}
				
				
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
		}
		
	}
	
	public void stop()
	{
		running=false;
	}

}
