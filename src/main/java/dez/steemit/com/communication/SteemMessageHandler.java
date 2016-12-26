package dez.steemit.com.communication;

import javax.websocket.MessageHandler;

public class SteemMessageHandler implements MessageHandler.Whole<String>{
	private String message;
	private CommunicationHandler communicationHandlerInstance;
	
	public SteemMessageHandler(CommunicationHandler communicationHandlerInstance) {
		this.communicationHandlerInstance = communicationHandlerInstance;
	}
	
	@Override
	public void onMessage(String message) {
		this.message = message;
		communicationHandlerInstance.countDownLetch();
	}

	public String getMessage() {
		return message;
	}
}
