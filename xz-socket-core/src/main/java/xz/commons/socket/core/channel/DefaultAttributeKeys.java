package xz.commons.socket.core.channel;

import io.netty.util.AttributeKey;
import xz.commons.socket.core.session.imp.SocketSession;

public interface DefaultAttributeKeys {
	
	AttributeKey<SocketSession> SESSION = AttributeKey.valueOf("SESSION");
	
}
