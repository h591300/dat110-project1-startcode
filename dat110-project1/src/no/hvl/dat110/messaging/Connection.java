package no.hvl.dat110.messaging;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import no.hvl.dat110.TODO;

public class Connection {

	private DataOutputStream outStream; // for writing bytes to the TCP connection
	private DataInputStream inStream; // for reading bytes from the TCP connection
	private Socket socket; // socket for the underlying TCP connection

	public Connection(Socket socket) {

		try {

			this.socket = socket;

			outStream = new DataOutputStream(socket.getOutputStream());

			inStream = new DataInputStream(socket.getInputStream());

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(Message message) {

		byte[] data;

		// encapsulate the data contained in the message and write to the output stream
		
		try {
			data = MessageUtils.encapsulate(message);

			outStream.write(data);
		} catch (IOException e) {
			System.out.println("Connection: " + e.getMessage());
			e.printStackTrace();
		}

	}

	public Message receive() {

		Message message = null;
		byte[] data;
		
		System.out.println("receiveing Message...");
				
		try {
			byte[] buffer = new byte[MessageUtils.SEGMENTSIZE];
			int i = 0;
			i = inStream.read(buffer,0, MessageUtils.SEGMENTSIZE);
			data = buffer;
			System.out.println("receive --> data: " + data.toString());
			
			message = MessageUtils.decapsulate(data);
			System.out.println("receive --> message: " + message.getData().length);

		} catch (IOException e) {
			System.out.println("error: ");
			e.printStackTrace();
		}
		
		
		return message;

	}

	// close the connection by closing streams and the underlying socket
	public void close() {

		try {

			outStream.close();
			inStream.close();

			socket.close();

		} catch (IOException ex) {

			System.out.println("Connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}