package secureChannel;

import java.io.*;
import java.net.*;

public class ServerConnection {
	InetAddress internetAddress;
	private static final int DEFAULT_PORT = 4444;
	private ObjectInputStream requests;
	private ObjectOutputStream responses;
	CryptoTools cryptoTools = new CryptoTools();
	private String establishedKey;
	private SocketIO socketIO = new SocketIO();
	ThreadListener threadListener;

	
	
	ServerConnection()
	{
		int port = DEFAULT_PORT;
		ServerSocket reception_socket = null;
		try {
			reception_socket = new ServerSocket(port);
			System.out.println("Started server on port:" + port);
		} catch (IOException io) {
			System.out.println("Cannot create server socket");
			System.exit(0);
		}
		
		establishClientConnection(reception_socket);
		
	}
	
	
	public void establishClientConnection(ServerSocket reception_socket)
	{
		for (;;) {
			Socket client_socket = null;

			try {
				client_socket = reception_socket.accept();
				System.out.println("Accepting request from "
						+ client_socket.getInetAddress());
			} catch (IOException ex) {
				System.out.println("Problem accepting client socket");
			}
			establishIO(client_socket);
		}
		
		
	}
	
	public String establishSecureKey(String key)
	{
		String nonce = Nonce.getNonce();
		String clientNonce=null;
		Packet packet;
		socketIO.sendPacket(nonce,key,responses);
		try {
			packet = (Packet)requests.readObject();
			clientNonce=cryptoTools.decrypt(packet.message, key);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return nonce+clientNonce;
	}
	
	public void establishIO(Socket socket)
	{
		try {
			responses = new ObjectOutputStream(socket.getOutputStream());
			requests = new ObjectInputStream(socket.getInputStream());
			responses.flush();
			run();

		} catch (IOException io) {
			System.out.println("Cannot open stream");

			
			System.exit(0);
		}
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
			socketIO.sendPacket(input,establishedKey,responses);
		}
	}
	
	public void run()
	{
		establishedKey=establishSecureKey("123");
		threadListener = new ThreadListener(requests,establishedKey);
		readInputToBeSent();
	}
	
	public static void main(String[] args) {
	ServerConnection c = new ServerConnection();
	
	}
	
}
