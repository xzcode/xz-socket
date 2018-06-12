package xz.commons.socket.core.config.scanner;

import io.github.lukehutch.fastclasspathscanner.FastClasspathScanner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import xz.commons.socket.core.annotation.*;
import xz.commons.socket.core.event.EventMethodInvoker;
import xz.commons.socket.core.event.EventMethodModel;
import xz.commons.socket.core.filter.MessageFilterMapper;
import xz.commons.socket.core.filter.MessageFilterModel;
import xz.commons.socket.core.filter.SocketMessageFilter;
import xz.commons.socket.core.mapper.SocketComponentObjectMapper;
import xz.commons.socket.core.message.MessageMethodInvoker;
import xz.commons.socket.core.message.RequestMethodModel;

import java.lang.reflect.Method;

/**
 * controller扫描工具
 *  扫描并缓存被注解的类信息
 * 
 * 
 * @author zai
 * 2017-07-29
 */
public class SocketComponentScanner {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SocketComponentScanner.class);
	
	public static void scan(
			SocketComponentObjectMapper componentObjectMapper,
			MessageMethodInvoker messageMethodInvoker,
			EventMethodInvoker eventMethodInvoker,
			String ...packageName
			) {
		
		if (packageName == null) {
			LOGGER.error(" packageName is null !! ");
			return;
		}
		
		//扫描指定包下的注解并生成关联
		
		new FastClasspathScanner(packageName)  
	    .matchClassesWithAnnotation(SocketComponent.class, clazz -> {
	    	try {
	    		Object clazzInstance = clazz.newInstance();
	    		componentObjectMapper.put(clazz, clazzInstance);
	    		
	    		
	    		SocketFilter filter = clazz.getAnnotation(SocketFilter.class);
	    		if (filter != null) {
	    			if (!(clazzInstance instanceof SocketMessageFilter)) {
	    				throw new RuntimeException("Class:"+clazz.getName()+" with annotation @"+ SocketFilter.class.getSimpleName() +" must implement interface:"+SocketMessageFilter.class.getName()+" ! ");
					}
	    			int value = filter.value();
	    			int order = filter.order();
					MessageFilterMapper.add(new MessageFilterModel(order != 0 ? order : value, (SocketMessageFilter)clazzInstance));
	    			
				}
	    		
				Method[] methods = clazz.getMethods();
				for (Method mtd : methods) {
					
					//扫描socketrequest
					SocketRequest requestMessage = mtd.getAnnotation(SocketRequest.class);
					if (requestMessage != null) {
						
						RequestMethodModel requestMethodModel = new RequestMethodModel();
						requestMethodModel.setMethod(mtd);
						requestMethodModel.setRequestTag(requestMessage.value());
						requestMethodModel.setSendMessageClass(mtd.getReturnType());
						Class<?>[] parameterTypes = mtd.getParameterTypes();
						if (parameterTypes != null && parameterTypes.length > 0) {
							requestMethodModel.setRequestMessageClass(parameterTypes[0]);
						}
						
						SocketResponse sendMessage = mtd.getAnnotation(SocketResponse.class);
						if (sendMessage != null) {
							requestMethodModel.setSendTag(sendMessage.value());
						}
						messageMethodInvoker.put(requestMessage.value(), requestMethodModel);
					}
					
					
					//扫描 socketevent
					SocketOnEvent socketOnEvent = mtd.getAnnotation(SocketOnEvent.class);
					if (socketOnEvent != null) {
						
						if (mtd.getParameterCount() > 0) {
							throw new RuntimeException("Annotation @"+ SocketOnEvent.class.getSimpleName() +" unsupport methods with parameters! ");
						}
						
						EventMethodModel eventMethodModel = new EventMethodModel();
						eventMethodModel
						.setEventTag(socketOnEvent.value())
						.addMethod(mtd);
						
						eventMethodInvoker.put(eventMethodModel);
					}
					
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
	    })
	    .scan();
	}

}
