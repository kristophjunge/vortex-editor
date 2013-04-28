package vortexeditor.editors;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.servlet.http.HttpServletRequest;
import org.eclipse.jetty.websocket.WebSocket;

public class WebSocketHandler
extends org.eclipse.jetty.websocket.WebSocketHandler {
	
	/**
	 * Connection array
	 */
	protected Set<VortexWebSocket> webSockets
		=new CopyOnWriteArraySet<VortexWebSocket>();
	
	/**
	 * Connect event handler
	 */
	public WebSocket doWebSocketConnect(
		HttpServletRequest request,
		String protocol
	) {
		
		return new VortexWebSocket();
		
	}
	
	/**
	 * Send message to all clients
	 * 
	 * @param data Data
	 */
	public void sendAll(String data)
	{
		for (VortexWebSocket webSocket:webSockets) {
			
			try {
				
				webSocket.connection.sendMessage(data);
				
			} catch (IOException e) {
				
				webSocket.connection.close();
				
			}
			
		}
		
	}
	
	/**
	 * Connection class
	 */
	protected class VortexWebSocket implements WebSocket.OnTextMessage {
		
		/**
		 * Connection handle
		 */
		protected Connection connection;
		
		/**
		 * Open event handler
		 * 
		 * @param connection Connection information
		 */
		public void onOpen(Connection connection) {
			
			this.connection=connection;
			
			webSockets.add(this);
			
			Server.clientConnected(connection);
			
		}
		
		/**
		 * Message event handler
		 * 
		 * @param data Data
		 */
		public void onMessage(String data) {
			
			System.out.print(data);
			
		}
		
		/**
		 * Close event handler
		 * 
		 * @param closeCode Close code
		 * @param message Message
		 */
		public void onClose(int closeCode, String message) {
			
			Server.clientDisconnected(this.connection);
			
			webSockets.remove(this);
			
		}
		
	}
	
}