package secureChannel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SocketIO {
	
	CryptoTools cryptoTools = new CryptoTools();


	public void sendPacket(String message, String key, int portNumber)
	{
	
		DatagramSocket socket;
		String cipherText = cryptoTools.encryptMessage(message, key);
		byte[] buffer = cipherText.getBytes();
		try {
			socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),portNumber);
			socket.send(packet);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public void sendNonce(String message, String key, int portNumber)
	{
	
		DatagramSocket socket;
		System.out.println("nonce is " +message);
		String cipherText = cryptoTools.encrypt(message, cryptoTools.SHA1(key));
		System.out.println("encrypted is " +cipherText);
		System.out.println("decrypted is " +cryptoTools.decrypt(cipherText, cryptoTools.SHA1(key)));
		System.out.println("the length is " + cipherText.length());
		byte[] buffer = cipherText.getBytes();
		try {
			socket = new DatagramSocket();
			DatagramPacket packet = new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),portNumber);
			socket.send(packet);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String receiveNonce( int portNumber, DatagramSocket socket, String key)
	{
		
		byte[] buffer = new byte[512];
		DatagramPacket packet;
		String message=null;
		//DatagramSocket socket;
		try {
			//socket = new DatagramSocket(portNumber);
			packet = new DatagramPacket(buffer,buffer.length);
			socket.receive(packet);
			message = new String(packet.getData(),0,packet.getLength());	
			System.out.println("recived message" + message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return cryptoTools.decrypt(message, cryptoTools.SHA1(key));
		
	}
	
	public String receivePacket( int portNumber, DatagramSocket socket)
	{
		
		byte[] buffer = new byte[1000];
		DatagramPacket packet;
		String message=null;
		//DatagramSocket socket;
		try {
			//socket = new DatagramSocket(portNumber);
			packet = new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),portNumber);
			socket.receive(packet);
			message = new String(packet.getData(),0,packet.getLength());	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
		
	}
	
}
