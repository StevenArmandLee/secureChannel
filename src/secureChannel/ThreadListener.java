/*
 * Name: Steven Lee
 * Student ID: 4643483
 * 
 */
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
				if(cryptoTools.authenticateMessage(message, key))
				{
					String plainText = cryptoTools.getMessage(message, key);
					
					if(plainText.toLowerCase().equals("exit"))
					{
						System.out.println("Connection has been disconnected by the other party!");
						System.exit(0);
					}
					System.out.println(plainText);
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
