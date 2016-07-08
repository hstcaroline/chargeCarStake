package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ArgueMessageNotification implements Serializable{
	private List<UserMessage> argueMessage = new ArrayList<UserMessage>();

	public List<UserMessage> getArgueMessage() {
		return argueMessage;
	}

	public void setArgueMessage(List<UserMessage> argueMessage) {
		this.argueMessage = argueMessage;
	}
	public int getArgueSize(){
		return argueMessage.size();
	}
	public void clear(){
		argueMessage.clear();
		
	}

}
