package no.hvl.dat110.rpc;

import no.hvl.dat110.TODO;
import no.hvl.dat110.messaging.*;

public class RPCClient {

	private MessagingClient msgclient;
	private Connection connection;
	
	public RPCClient(String server, int port) {
	
		msgclient = new MessagingClient(server,port);
	}
	
	public void connect() {
		
		// connect using the underlying messaging layer connection
		
		connection = msgclient.connect();
	}
	
	public void disconnect() {
		
		// disconnect/close the underlying messaging connection
		
		if(connection != null) {
			connection.close();
		}
	}
	
	public byte[] call(byte rpcid, byte[] params) {
		
		byte[] returnval = null;
		
		/* 
		Make a remote call on the RPC server by sending an RPC request message
		and receive an RPC reply message
		
		params is the marshalled parameters from the client-stub
				
		The rpcid, params, and return value must be encapsulated/decapsulated
		according to the RPC message format
		*/
		if(connection == null) {
			connect();
		}
		System.out.println("[RPCClient]: calling encapsulate with rpcid:" + rpcid + ", and params: " + params.toString());
		byte[] rpcmsg = RPCUtils.encapsulate(rpcid, params);
		
		System.out.println("[RPCClient]: making Message");
		Message message = new Message(rpcmsg);
		
		System.out.println("[RPCClient]: connection.send");
		connection.send(message);
		
		System.out.println("[RPCClient]: connection.receive");
		Message m = connection.receive();
		
		returnval = RPCUtils.decapsulate(m.getData());		
		System.out.println("[RPCClient]: returnval:" + returnval);
		
		return returnval;
		
	}

}
