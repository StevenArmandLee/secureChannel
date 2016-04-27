package secureChannel;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
public class ClientConnection {

	private static final int DEFAULT_PORT = 4444;
	private static final String hostName = "localhost";
	private ObjectInputStream responseStream;
	private ObjectOutputStream requestStream;
	private InetAddress ina;
	private static Socket socket;
	private String establishedKey;
	private SocketIO socketIO = new SocketIO();
	CryptoTools cryptoTools = new CryptoTools();
	ThreadListener threadListener;
	
	ClientConnection()
	{
		try {
			ina = InetAddress.getByName(hostName);
		} catch (UnknownHostException u) {
			System.out.print("Cannot find host name");
			System.exit(0);
		}

		try {
			socket = new Socket(ina, DEFAULT_PORT);
		} catch (IOException ex) {
			System.out.print("Cannot connect to host");
			System.exit(1);
		}

		// Get I/O streams make the ObjectStreams
		// for serializable objects
		try {
			responseStream = new ObjectInputStream(socket.getInputStream());
			requestStream = new ObjectOutputStream(socket.getOutputStream());
			requestStream.flush();
		} catch (IOException io) {

			System.out.println("Failed to get socket streams");
			System.exit(1);
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
			socketIO.sendPacket(input,establishedKey,requestStream);
		}
	}
	
	public void messageListener()
	{
		establishedKey=establishSecureKey("123");
		threadListener = new ThreadListener(responseStream,establishedKey);
		
		readInputToBeSent();
	}
	
	
	
	
	
	public String establishSecureKey(String key)
	{
		String nonce = Nonce.getNonce();
		String hostNonce=null;
		Packet packet;
		
		try {
			packet = (Packet)responseStream.readObject();
			hostNonce=cryptoTools.getMessage(packet.getMessage(), key);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		socketIO.sendPacket(nonce,key,requestStream);
		
		return hostNonce+nonce;
	}
	
	public static void main(String[] args) {
		ClientConnection c = new ClientConnection();
		c.messageListener();
		
		
			
		
	}
}
