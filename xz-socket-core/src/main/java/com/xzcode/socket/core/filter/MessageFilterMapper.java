package com.xzcode.socket.core.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 消息过滤器集合
 *
 * @author zai
 * 2018-12-20 10:15:31
 */
public class MessageFilterMapper {
	
	private static final List<MessageFilterModel> FILTERS = new ArrayList<>(1);
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MessageFilterMapper.class);
	
	
	public static void add(MessageFilterModel filterModel) {
		FILTERS.add(filterModel);
		if (FILTERS.size() > 1) {
			sort();
		}
	}
	
	public static void sort() {
		FILTERS.sort(new Comparator<MessageFilterModel>() {

			@Override
			public int compare(MessageFilterModel o1, MessageFilterModel o2) {
				if (o1.getOrder() > o2.getOrder()) {
					return 1;
				}
				return -1;
			}
		});
	}
	
	/**
	 * 顺序执行过滤器
	 * @param requestTag
	 * @param message
	 * @return
	 * 
	 * @author zai
	 * 2017-09-27
	 */
	public static boolean doFilters(String requestTag, Object message) {
		SocketMessageFilter filter = null;
		for(int i = 0; i < FILTERS.size(); i ++) {
			filter = FILTERS.get(i).getFilter();
			if (!FILTERS.get(i).getFilter().doFilter(requestTag, message)) {
				if (LOGGER.isDebugEnabled()) {
					LOGGER.debug("Message filtered by {}, requestTag:{} .", filter.getClass().getName(),requestTag);					
				}
				return false;
			}
		}
		return true;
	}

}
