package xz.commons.socket.core.sender;

public class SendModel {
	
	private String sendTag;
	private Object message;
	
	public SendModel(String sendTag, Object message) {
		this.sendTag = sendTag;
		this.message = message;
	}

	public static SendModel create(String sendTag, Object message) {
		return new SendModel(sendTag, message);
	}

	public String getSendTag() {
		return sendTag;
	}

	public void setSendTag(String sendTag) {
		this.sendTag = sendTag;
	}


	public Object getMessage() {
		return message;
	}

	public void setMessage(Object message) {
		this.message = message;
	}
	
	
	

}
