package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NormalMessageNotification implements Serializable{
	private List<UserMessage> normalMessage = new ArrayList<UserMessage>();

	public List<UserMessage> getNormalMessage() {
		return normalMessage;
	}

	public void setNormalMessage(List<UserMessage> normalMessage) {
		this.normalMessage = normalMessage;
	}
	public int getNormalSize(){
		return normalMessage.size();
	}
	public void clear(){
		normalMessage.clear();
		
	}
}
