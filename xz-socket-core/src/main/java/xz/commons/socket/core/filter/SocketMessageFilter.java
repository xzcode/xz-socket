package xz.commons.socket.core.filter;

public interface SocketMessageFilter {
	
	public boolean doFilter(int requestTag, Object message);

}
