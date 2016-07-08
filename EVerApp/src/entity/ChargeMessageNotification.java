package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ChargeMessageNotification implements Serializable{
	private List<UserMessage> chargeMessage = new ArrayList<UserMessage>();

	public List<UserMessage> getChargeMessage() {
		return chargeMessage;
	}

	public void setChargeMessage(List<UserMessage> chargeMessage) {
		this.chargeMessage = chargeMessage;
	}
	public int getChargeSize(){
		return chargeMessage.size();
	}
	public void clear(){
		chargeMessage.clear();
		
	}
}
