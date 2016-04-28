package secureChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class ClientConnection {

	private static final int DEFAULT_SEND_PORT = 4445;
	private static final int DEFAULT_RECEIVE_PORT = 4444;
	private static final String hostName = "localhost";
	private ObjectInputStream responseStream;
	private ObjectOutputStream requestStream;
	private InetAddress ina;
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
			String input = Keyboard.readString("test:");
			if(input.toLowerCase().equals("exit"))
			{
				threadListener.stop();
				System.exit(0);
			}
			socketIO.sendPacket(input,establishedKey,DEFAULT_SEND_PORT);
		}
	}
	
	public void messageListener()
	{
		establishedKey=establishSecureKey("123");
		threadListener = new ThreadListener(socket,establishedKey,DEFAULT_RECEIVE_PORT,reciverSocket);
		
		readInputToBeSent();
	}
	
	
	
	
	
	public String establishSecureKey(String key)
	{
		String nonce = Nonce.getNonce();
		String hostNonce=null;
			
			hostNonce=socketIO.receiveNonce(DEFAULT_RECEIVE_PORT,reciverSocket,key);
			socketIO.sendNonce(nonce,key,DEFAULT_SEND_PORT);
			//nonce = cryptoTools.encrypt(nonce, cryptoTools.SHA1(key));
			//System.out.println(nonce);
			//System.out.println(hostNonce);
			System.out.println("the recived decrypt"+hostNonce);
			System.out.println("length of recived nonce " + hostNonce.length());
			return cryptoTools.SHA1(hostNonce+nonce);
	}
	
	public static void main(String[] args) {
		ClientConnection c = new ClientConnection();
		c.messageListener();
		//CryptoTools.encryptMessage("test", "aaaa");
		//System.out.println(CryptoTools.encryptMessage("test", "aaaa"));
		//System.out.println(CryptoTools.getHash(CryptoTools.encryptMessage("test", "aaaa"),"aaaa"));
		//System.out.println(CryptoTools.SHA1("aaaa"+"test"+"aaaa"));
		
		
			
		
	}
}
