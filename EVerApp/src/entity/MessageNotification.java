package entity;

import java.io.Serializable;
import java.util.List;

public class MessageNotification implements Serializable{
	public ChargeMessageNotification chargeMessageNotification = new ChargeMessageNotification();
	public ArgueMessageNotification argueMessageNotification = new ArgueMessageNotification();
	public NormalMessageNotification normalMessageNotification = new NormalMessageNotification();
	public List<UserMessage> getChargeMessage() {
		return chargeMessageNotification.getChargeMessage();
	}
	public void setChargeMessage(List<UserMessage> chargeMessage) {
		chargeMessageNotification.setChargeMessage(chargeMessage);
	}
	public List<UserMessage> getArgueMessage() {
		return argueMessageNotification.getArgueMessage();
	}
	public void setArgueMessage(List<UserMessage> argueMessage) {
		argueMessageNotification.setArgueMessage(argueMessage);
	}
	public List<UserMessage> getNormalMessage() {
		return normalMessageNotification.getNormalMessage();
	}
	public void setNormalMessage(List<UserMessage> normalMessage) {
		normalMessageNotification.setNormalMessage(normalMessage);
	}
	
	public int getChargeSize(){
		return chargeMessageNotification.getChargeSize();
	}
	public int getArgueSize(){
		return argueMessageNotification.getArgueSize();
	}
	public int getNormalSize(){
		return normalMessageNotification.getNormalSize();
	}
	
	public void clear(){
		chargeMessageNotification.clear();
		argueMessageNotification.clear();
		normalMessageNotification.clear();
	}
}
