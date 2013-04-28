package vortexeditor.editors;

import org.eclipse.core.resources.IFile;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.websocket.WebSocket.Connection;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.console.ConsolePlugin;
import org.eclipse.ui.console.IConsole;
import org.eclipse.ui.console.IConsoleManager;
import org.eclipse.ui.console.MessageConsole;
import org.eclipse.ui.console.MessageConsoleStream;

public class Server {
	
	/**
	 * Singleton instance
	 */
	protected static Server instance=null;
	
	/**
	 * Server initialized 
	 */
	protected static Boolean initialized=false;
	
	/**
	 * Server running
	 */
	protected static Boolean running=false;
	
	/**
	 * Number of documents opened in vortex editor
	 */
	protected static Integer documentCount=0; 
	
	/**
	 * Server port
	 */
	protected static Integer port=8081;
	
	/**
	 * Eclipse console handle
	 */
	protected static MessageConsole console=null;
	
	/**
	 * WebSocket handler
	 */
	protected static WebSocketHandler handler=null;
	
	/**
	 * Jetty server
	 */
	protected static org.eclipse.jetty.server.Server server=null;
	
	/**
	 * Constructor
	 */
	public Server() {
		
	}
	
	/**
	 * Singelton implementation
	 * 
	 * @return vortexeditor.editors.Server
	 */
	public static Server getInstance() {
		
		if (instance==null) {
			instance=new Server();
		}
		
		return instance;
		
	}
	
	/**
	 * Start server
	 */
	public static void start() {
		
		if (!initialized) {
			
			console=Server.findConsole("VortexConsole");
			
			try {
				
				// Create Jetty server
				server=new org.eclipse.jetty.server.Server(port);
				
				// Register WebSocketHandler
				handler=new WebSocketHandler();
				handler.setHandler(new DefaultHandler());
				server.setHandler(handler);
				
				
			} catch (Throwable e) {
				
				e.printStackTrace();
				
			}
			
			initialized=true;
			
		}
		
		try {
			
			// Start Jetty server
			server.start();
			
			running=true;
			
			System.out.println("Vortex: Server started");
			
			log("Vortex: Server started ("+port+")");
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Stop server
	 */
	public static void stop() {
		
		try {
			
			server.stop();
			
			running=false;
			
			System.out.println(
				"Vortex: Server stopped (No more documents to serve)"
			);
			
			log("Vortex: Server stopped (No more documents to serve)");
			
		} catch (Throwable e) {
			
			e.printStackTrace();
			
		}
		
	}
	
	/**
	 * Client connected event handler
	 * 
	 * @param connection Connection information
	 */
	public static void clientConnected(Connection connection) {
		
		System.out.println("Vortex: Client connected ("+connection+")");
		
		log("Vortex: Client connected ("+connection+")");
		
	}
	
	/**
	 * Client disconnected event handler
	 * 
	 * @param connection Connection information
	 */
	public static void clientDisconnected(Connection connection) {
		
		System.out.println("Vortex: Client disconnected ("+connection+")");
		
		log("Vortex: Client disconnected ("+connection+")");
		
	}
	
	/**
	 * Document opened event handler
	 * 
	 * @param document Related document
	 */
	public static void documentOpened(IDocument document) {
		
		documentCount++;
		
		if (!running) {
			start();
		}
		
		System.out.println("Vortex: Document opened");
		
	}
	
	/**
	 * Document closed event handler
	 * 
	 * @param document Related document
	 */
	public static void documentClosed(IDocument document) {
		
		documentCount--;
		
		System.out.println("Vortex: Document closed");
		
		if (documentCount==0) {
			stop();
		}
		
	}
	
	/**
	 * Document changed event handler
	 * 
	 * @param event  Event information
	 * @param file File information
	 */
	public static void documentChanged(DocumentEvent event, IFile file) {
		
		String path=file.getFullPath().toOSString();
		
		String header="{"
			+"\"file\": \""+file.getName()+"\""	
			+",\"path\": \""+path+"\""
			+",\"offset\": "+event.fOffset
			+",\"length\": "+event.fLength
		+"}";
		
		System.out.println(
			"Changed ("+event.fOffset+":"+event.fLength+") "+event.fText
		);
		
		String message=header+"\n\n"+event.fDocument.get();
		
		handler.sendAll(message);
		
	}
	
	/**
	 * Send a log message to the eclipse console
	 * 
	 * @param msg Message
	 */
	protected static void log(String msg) {
		
		MessageConsoleStream out=console.newMessageStream();
		
		out.println(msg);
		
	}
	
	/**
	 * Returns a handle to the eclipse console
	 */
	protected static MessageConsole findConsole(String name) {
		
		ConsolePlugin plugin=ConsolePlugin.getDefault();
		
		IConsoleManager consoleManager=plugin.getConsoleManager();
		
		IConsole[] existing=consoleManager.getConsoles();
		
		for (int i=0;i<existing.length;i++) {
			if (name.equals(existing[i].getName())) {
				return (MessageConsole) existing[i];
			}
		}
		
		//no console found, so create a new one
		MessageConsole console=new MessageConsole(name,null);
		
		consoleManager.addConsoles(new IConsole[]{console});
		
		return console;
		
	}
	
}