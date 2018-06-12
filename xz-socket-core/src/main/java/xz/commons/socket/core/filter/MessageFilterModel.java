package xz.commons.socket.core.filter;

public class MessageFilterModel {
	
	private int order;
	
	private SocketMessageFilter filter;

	public MessageFilterModel(int order, SocketMessageFilter filter) {
		super();
		this.order = order;
		this.filter = filter;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public SocketMessageFilter getFilter() {
		return filter;
	}

	public void setFilter(SocketMessageFilter filter) {
		this.filter = filter;
	}
	
	
	

}
