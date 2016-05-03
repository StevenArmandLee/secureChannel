/*
 * Name: Steven Lee
 * Student ID: 4643483
 * 
 */
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
		key = cryptoTools.SHA1(key);
		String nonce = Nonce.getNonce();
		String clientNonce=null;
		String hashedClientServerNonce = null;
		socketIO.sendPacket(cryptoTools.encrypt(nonce, cryptoTools.SHA1(key)),DEFAULT_SEND_PORT);
		clientNonce=socketIO.receiveNonce(DEFAULT_RECEIVE_PORT,reciverSocket,key);
		hashedClientServerNonce =  cryptoTools.SHA1(nonce+clientNonce);
		
		System.out.println("The nounce is :" + nonce);
		System.out.println("The nounce of the other party is :" + clientNonce);
		System.out.println("finished exchanging protocol. With established key " + hashedClientServerNonce + "\n");
		
		return hashedClientServerNonce;
	}
	
	
	
	public void readInputToBeSent()
	{
		
		while(true)
		{
			String input = Keyboard.readString("Message to be sent: ");
			String chiperText = cryptoTools.encryptMessage(input, establishedKey);
			socketIO.sendPacket(chiperText,DEFAULT_SEND_PORT);
			if(input.toLowerCase().equals("exit"))
			{
				threadListener.stop();
				System.exit(0);
			}
			
		}
	}
	
	public void run()
	{
		System.out.println("waiting for client to connect");
		socketIO.receivePacket(DEFAULT_RECEIVE_PORT, reciverSocket); // to wait untill there is a client
		System.out.println("connected to the client");
		establishedKey=establishSecureKey(FileIO.readFile("PW.txt"));
		System.out.println("starting thread for listening to incoming message!");
		threadListener = new ThreadListener(socket,establishedKey,DEFAULT_RECEIVE_PORT,reciverSocket);
		readInputToBeSent();
	}
	
	public static void main(String[] args) {
	ServerConnection c = new ServerConnection();
	c.run();
	
	}
	
}
