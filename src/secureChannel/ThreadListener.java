package secureChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.NoSuchAlgorithmException;

public class ThreadListener implements Runnable{
	DatagramSocket socket;
	private Thread thread;
	private String key;
	private int port;
	CryptoTools cryptoTools = new CryptoTools();
	boolean running = true;
	private SocketIO socketIO = new SocketIO();
	private DatagramSocket reciverSocket;
	
	ThreadListener(DatagramSocket socket, String key,int port, DatagramSocket reciverSocket)
	{
		this.reciverSocket = reciverSocket;
		this.socket=socket;
		this.key=key;
		this.port = port;
		thread = new Thread(this);
		thread.start();
	}

	@Override
	public void run() {
		 
		while(running)
		{
			
				byte[] buffer = new byte[1000];
				DatagramPacket packet;
				String message = socketIO.receivePacket(port,reciverSocket);
				//cryptoTools.getMessage(packet.getMessage(), key);
				//cryptoTools.getHash(packet.getMessage(),key);
				if(cryptoTools.authenticateMessage(message, key))
				{
					System.out.println(cryptoTools.getMessage(message, key));
					
				}
				else
				{
					System.out.println("decryption error");
				}
				
				
				
			
		}
		
	}
	
	public void stop()
	{
		running=false;
	}

}
