package secureChannel;

import java.io.*;
import java.net.*;

public class ServerConnection {
	InetAddress internetAddress;
	private static final int DEFAULT_SEND_PORT = 4444;
	private static final int DEFAULT_RECEIVE_PORT = 4445;
	CryptoTools cryptoTools = new CryptoTools();
	private String establishedKey;
	private SocketIO socketIO = new SocketIO();
	ThreadListener threadListener;
	DatagramSocket socket = null;
	private DatagramSocket reciverSocket;
	
	
	ServerConnection()
	{
		try {
			reciverSocket = new DatagramSocket(DEFAULT_RECEIVE_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	public String establishSecureKey(String key)
	{
		String nonce = Nonce.getNonce();
		String clientNonce=null;
		
		socketIO.sendNonce(nonce,key,DEFAULT_SEND_PORT);
		clientNonce=socketIO.receiveNonce(DEFAULT_RECEIVE_PORT,reciverSocket,key);
		//nonce = cryptoTools.encrypt(nonce, cryptoTools.SHA1(key));
		//System.out.println(nonce);
		//System.out.println(clientNonce);
		System.out.println("the recived decrypt"+clientNonce);
		System.out.println("length of recived nonce " + clientNonce.length());
		return cryptoTools.SHA1(nonce+clientNonce);
	}
	
	
	
	public void readInputToBeSent()
	{
		
		while(true)
		{
			String input = Keyboard.readString("test:");
			if(input.toLowerCase().equals("exit"))
			{
				threadListener.stop();
				System.exit(0);
				//TODO break from the while loop and close connectionand stop the thread
			}
			socketIO.sendPacket(input,establishedKey,DEFAULT_SEND_PORT);
		}
	}
	
	public void run()
	{
		establishedKey=establishSecureKey("123");
		threadListener = new ThreadListener(socket,establishedKey,DEFAULT_RECEIVE_PORT,reciverSocket);
		readInputToBeSent();
	}
	
	public static void main(String[] args) {
	ServerConnection c = new ServerConnection();
	c.run();
	
	}
	
}
