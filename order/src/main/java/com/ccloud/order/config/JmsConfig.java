package com.ccloud.order.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.boot.autoconfigure.jms.DefaultJmsListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.TransactionAwareConnectionFactoryProxy;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.transaction.PlatformTransactionManager;

import javax.jms.ConnectionFactory;

/**
 * 功能描述:
 * jms配置
 * @Author: 腾云先生
 * @Date: 2020/03/14 13:11
 */
@Configuration
public class JmsConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        ConnectionFactory cf = new ActiveMQConnectionFactory("tcp://192.168.31.30:61616");
        // 事务同步 让消息数据源和数据库数据源进行事务同步
        TransactionAwareConnectionFactoryProxy proxy = new TransactionAwareConnectionFactoryProxy();
        proxy.setTargetConnectionFactory(cf);
        proxy.setSynchedLocalTransactionAllowed(true);
        return proxy;
    }

    @Bean
    public JmsTemplate jmsTemplate(ConnectionFactory connectionFactory, MessageConverter jacksonJmsMessageConverter) {
        JmsTemplate jmsTemplate = new JmsTemplate(connectionFactory);
        jmsTemplate.setMessageConverter(jacksonJmsMessageConverter);
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    @Bean
    public JmsListenerContainerFactory<?> msgFactory(ConnectionFactory cf,
                                                     PlatformTransactionManager transactionManager,
                                                     DefaultJmsListenerContainerFactoryConfigurer configurer) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        configurer.configure(factory, cf);
        factory.setReceiveTimeout(10000L);
        factory.setCacheLevelName("CACHE_CONNECTION");
        factory.setTransactionManager(transactionManager);
        factory.setConcurrency("10");
        return factory;
    }

    /**
     * 功能描述:
     * 主要用于消息对象与java对象的相互转换
     * @Author: 腾云先生
     * @Date: 2020/03/14 13:14
     */
    @Bean
    public MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}
