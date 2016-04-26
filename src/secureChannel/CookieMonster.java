package secureChannel;

import java.awt.*;
import java.net.*;

public class CookieMonster {
    public static void main(String args[]){
        DatagramSocket socket = null;
        DatagramPacket packet = null;

	// create a receive buffer
	    byte[] buffer = new byte[1024];

	// create a packet to receive the buffer
	    packet = new DatagramPacket(buffer, buffer.length);

	// now create a socket to listen in
	    try	{
	        socket = new DatagramSocket(8505);
	    } catch(Exception e){
	        System.out.println("Error! - " + e.toString());
	    }

	// now sit in an infinite loop and eat cookies!
	    while(true)	{
	        try {
	            socket.receive(packet);
	        } catch(Exception e) {
		        System.out.println("Error! - " + e.toString());
	        }

	    // extract the cookie
	        String cookieString = new String(buffer);

	    // now show what we got!
	        System.out.println("Yummy!  Got a cookie with " + cookieString);
	    }
    }
}
