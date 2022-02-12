package no.hvl.dat110.messaging;

import no.hvl.dat110.TODO;

public class Message {

	private byte[] data;

	public Message(byte[] data) {
		
		if (data != null || data.length < 128) {
			this.data = data;
		}
		else {
			System.out.println("Feil format på data");
		}
			
	}

	public byte[] getData() {
		return this.data; 
	}

}
