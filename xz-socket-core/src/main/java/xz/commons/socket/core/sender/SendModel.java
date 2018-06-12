package xz.commons.socket.core.sender;

public class SendModel {
	
	private int sendTag;
	private Object message;
	
	public SendModel(int sendTag, Object message) {
		this.sendTag = sendTag;
		this.message = message;
	}

	public static SendModel create(int sendTag, Object message) {
		return new SendModel(sendTag, message);
	}

	public int getSendTag() {
		return sendTag;
	}

	public void setSendTag(int sendTag) {
		this.sendTag = sendTag;
	}


	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	
	

}
