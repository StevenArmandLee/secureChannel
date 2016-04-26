package secureChannel;

import java.awt.*;
import java.net.*;
import java.awt.event.*;
import javax.swing.*;

public class CookieBakery extends JFrame implements ActionListener{
    Scrollbar chipsScrollbar;
    TextField chips;
    JButton sendButton;
    JButton exitButton;
    JLabel instructionField;
    InetAddress internetAddress;

    CookieBakery()    {
		// initialize the application frame
		super("Cookies Order");
		setSize(310, 200);

		// initialize the network
		try{
		    internetAddress = InetAddress.getByName("localhost");
		}catch(Exception exc){
		    System.out.println("Error! - " + exc.toString());
		}

		// create slider for number of chips
		chipsScrollbar = new Scrollbar(Scrollbar.HORIZONTAL, 20,1, 1, 10);
		chipsScrollbar.setBounds(10, 50, 290, 20);
		Container c = getContentPane();
		c.add(chipsScrollbar,BorderLayout.NORTH);

		// create buttons
		sendButton = new JButton("Send Cookie");
		JPanel panel = new JPanel();
		panel.add(sendButton);

		exitButton = new JButton("Exit");
		panel.add(exitButton);
		exitButton.setBackground(Color.red);
		c.add(panel,BorderLayout.SOUTH);

		// create instruction field
		instructionField = new JLabel("Select Number of Chips!");
		instructionField.reshape(10, 380, 290, 30);
		instructionField.setHorizontalAlignment(SwingConstants.CENTER);
		c.add(instructionField,BorderLayout.NORTH);

		sendButton.addActionListener(this);
		exitButton.addActionListener(this);
    }

    public void actionPerformed(ActionEvent evt) {
	    if(evt.getSource() == sendButton){
	        // determine the number of chips
	        int numChips = chipsScrollbar.getValue();
	        String messageChips = numChips + " chips";

	        // convert the chip message to byte form
	        int msgLength = messageChips.length();
	        byte[] message = new byte[msgLength];
	        message = messageChips.getBytes();

	        // send a message
	        try {
	            // format the cookie into a UDP packet
	            instructionField.setText("Sending Cookie...");
	            DatagramPacket packet = new DatagramPacket(
					         message, msgLength, internetAddress, 8505);

	            // send the packet to the server
	            DatagramSocket socket = new DatagramSocket();
	            socket.send(packet);
	        } catch(Exception exc){
	            System.out.println("Error! - " + exc.toString());
	        }

	        // display final result
	        instructionField.setText("Sent Cookie!");
	     } else if(evt.getSource() == exitButton){
	           System.exit(0);
	     }

    }

    public static void main(String args[]){
	    CookieBakery cookies = new CookieBakery();
	    cookies.show();
    }
}
