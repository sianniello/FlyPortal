package flyportalws;

import java.io.IOException;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/echoWS")
public class FlyPortalWebSocket {

	private static Set<Session> allSessions;

	@OnMessage
	public String onMessage(String message, Session session) {
		allSessions = session.getOpenSessions();
		for (Session sess: allSessions){          
			try{   
				if(!sess.equals(session))
				sess.getBasicRemote().sendText("");
			} catch (IOException ioe) {        
				System.out.println(ioe.getMessage());         
			}   
		}
		return null;   
	}

	@OnOpen
	public void onOpen(Session session) {
		allSessions = session.getOpenSessions();
	}

	@OnError
	public void onError(Throwable t) {
	}

	@OnClose
	public void onClose(Session session) {

		System.out.println(session.getId() + " ha chiuso la sessione");
	}

}
