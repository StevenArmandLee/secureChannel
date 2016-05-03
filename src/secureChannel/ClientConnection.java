/*
 * Name: Steven Lee
 * Student ID: 4643483
 * 
 */


package secureChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class ClientConnection {

	private static final int DEFAULT_SEND_PORT = 4445;
	private static final int DEFAULT_RECEIVE_PORT = 4444;
	private DatagramSocket socket;
	private String establishedKey;
	private SocketIO socketIO = new SocketIO();
	CryptoTools cryptoTools = new CryptoTools();
	ThreadListener threadListener;
	private DatagramSocket reciverSocket;
	
	ClientConnection()
	{
		try {
			reciverSocket = new DatagramSocket(DEFAULT_RECEIVE_PORT);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		socketIO.sendNonce("a","a",DEFAULT_SEND_PORT);
		establishedKey=establishSecureKey(FileIO.readFile("PW.txt"));
		System.out.println("starting thread for listening to incoming message!");
		threadListener = new ThreadListener(socket,establishedKey,DEFAULT_RECEIVE_PORT,reciverSocket);
		readInputToBeSent();
	}
	
	
	
	
	
	public String establishSecureKey(String key)
	{
		key = cryptoTools.SHA1(key);
		String nonce = Nonce.getNonce();
		String hostNonce=null;
		String hashedClientServerNonce = null;
			hostNonce=socketIO.receiveNonce(DEFAULT_RECEIVE_PORT,reciverSocket,key);
			socketIO.sendPacket(cryptoTools.encrypt(nonce, cryptoTools.SHA1(key)),DEFAULT_SEND_PORT);
			hashedClientServerNonce =  cryptoTools.SHA1(hostNonce+nonce);
			
			System.out.println("The nounce is :" + nonce);
			System.out.println("The nounce of the other party is :" + hostNonce);
			System.out.println("finished exchanging protocol. With established key " + hashedClientServerNonce + "\n");
			
			return hashedClientServerNonce;
	}
	
	public static void main(String[] args) {
		ClientConnection c = new ClientConnection();
		c.run();
		
	}
}
