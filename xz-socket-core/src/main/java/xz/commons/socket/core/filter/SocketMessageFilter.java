package xz.commons.socket.core.filter;

public interface SocketMessageFilter {
	
	public boolean doFilter(String requestTag, Object message);

}
