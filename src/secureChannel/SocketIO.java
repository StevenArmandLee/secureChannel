/*
 * Name: Steven Lee
 * Student ID: 4643483
 * 
 */
package secureChannel;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SocketIO {
	
	CryptoTools cryptoTools = new CryptoTools();


	public void sendPacket(String message, int portNumber)
	{
	
		DatagramSocket socket;
		byte[] buffer=null;
				try {
					buffer = message.getBytes("ISO-8859-1");
				} catch (UnsupportedEncodingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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
		String cipherText = cryptoTools.encrypt(message, cryptoTools.SHA1(key));
		byte[] buffer=null;
		try {
			buffer = cipherText.getBytes("ISO-8859-1");
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
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
		return cryptoTools.decrypt(receivePacket(portNumber,socket), cryptoTools.SHA1(key));
		
	}
	
	public String receivePacket( int portNumber, DatagramSocket socket)
	{
		
		byte[] buffer = new byte[1000];
		DatagramPacket packet;
		String message=null;
		try {
			packet = new DatagramPacket(buffer,buffer.length,InetAddress.getByName("localhost"),portNumber);
			socket.receive(packet);
			message = new String(packet.getData(),0,packet.getLength(),"ISO-8859-1");	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return message;
		
	}
	
}
