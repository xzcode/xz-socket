package xz.socket.autoconfig;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import xz.commons.socket.core.config.SocketServerConfig;
import xz.commons.socket.core.starter.SocketServerStarter;
import xz.commons.socket.core.starter.impl.DefaultSocketServerStarter;
import xz.commons.socket.core.starter.impl.WebSocketServerStarter;

import java.util.Map;

@Configuration
@ConditionalOnProperty(prefix = SocketAutoConfig.PROPERTIES_PREFIX, name = "enabled", havingValue = "true")
@ConditionalOnMissingBean({SocketServerStarter.class})
public class SocketAutoConfig implements ApplicationContextAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(SocketAutoConfig.class);

    public static final String PROPERTIES_PREFIX = "sourcemuch.commons.socket";

    private ApplicationContext applicationContext;

    @Bean
    public SocketServerStarter socketServerStarter() {

        SocketServerConfig config = socketServerConfig();

        LOGGER.info(config.toString());


        beanDefinitionRegistry(config);

        if (SocketServerConfig.ServerTypeConstants.SOCKET.equals(config.getServerType())) {
            return new DefaultSocketServerStarter(config);

        } else if (SocketServerConfig.ServerTypeConstants.WEBSOCKET.equals(config.getServerType())) {
            return new WebSocketServerStarter(config);
        } else {
            Assert.state(true, "Unsupported serverType:" + config.getServerType() + "!");
        }

        return null;
    }

    @Bean
    @ConfigurationProperties(prefix = SocketAutoConfig.PROPERTIES_PREFIX)
    public SocketServerConfig socketServerConfig() {
        return new SocketServerConfig();
    }

    /**
     * 动态注册bean到spring
     *
     * @author zai
     * 2017-10-30
     */
    public void beanDefinitionRegistry(SocketServerConfig config) {

        //获取BeanFactory
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) this.applicationContext.getAutowireCapableBeanFactory();

        Map<Class<?>, Object> map = config.getComponentObjectMapper().getComponentMap();
        for (Class<?> key : map.keySet()) {

            String beanName = StringUtils.uncapitalize(key.getSimpleName());


            //创建bean信息.
            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(key);

            //动态注册bean.
            defaultListableBeanFactory.registerBeanDefinition(StringUtils.uncapitalize(key.getSimpleName()), beanDefinitionBuilder.getBeanDefinition());
            LOGGER.info("Socket server bean defind:{}", applicationContext.getBean(beanName));
            map.put(key, applicationContext.getBean(beanName));
        }


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

}
