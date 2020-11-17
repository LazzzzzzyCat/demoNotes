package com.demo.config;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.RedeliveryPolicy;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.config.JmsListenerContainerFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsTemplate;

import javax.jms.*;

/**
 * activemq配置
 *
 * @author huwj
 * @date 2020-08-04 14:04
 */
@Configuration
@EnableJms
@Import(ActiveMQProperties.class)
public class ActiveMQConfig {

    @Bean
    public RedeliveryPolicy redeliveryPolicy() {
        RedeliveryPolicy redeliveryPolicy = new RedeliveryPolicy();
        //是否在每次尝试重新发送失败后,增长这个等待时间
        redeliveryPolicy.setUseExponentialBackOff(true);
        //重发次数,默认为6次   这里设置为10次
        redeliveryPolicy.setMaximumRedeliveries(3);
        //重发时间间隔,默认为1秒
        redeliveryPolicy.setInitialRedeliveryDelay(1000);
        //第一次失败后重新发送之前等待500毫秒,第二次失败再等待500 * 2毫秒,这里的2就是value
        redeliveryPolicy.setBackOffMultiplier(2);
        //是否避免消息碰撞
        redeliveryPolicy.setUseCollisionAvoidance(false);
        //设置重发最大拖延时间-1 表示没有拖延只有UseExponentialBackOff(true)为true时生效
        redeliveryPolicy.setMaximumRedeliveryDelay(-1);
        return redeliveryPolicy;
    }

    @Bean
    public ActiveMQConnectionFactory activeMQConnectionFactory(ActiveMQProperties properties, RedeliveryPolicy redeliveryPolicy) {
        ActiveMQConnectionFactory activeMQConnectionFactory =
                new ActiveMQConnectionFactory(
                        properties.getUser(),
                        properties.getPassword(),
                        properties.getBrokerUrl());
        activeMQConnectionFactory.setRedeliveryPolicy(redeliveryPolicy);
        return activeMQConnectionFactory;
    }

    @Bean
    public JmsTemplate jmsTemplate(ActiveMQConnectionFactory activeMQConnectionFactory, Queue queue) {
        JmsTemplate jmsTemplate = new JmsTemplate();
        //进行持久化配置 1表示非持久化，2表示持久化
        jmsTemplate.setDeliveryMode(DeliveryMode.PERSISTENT);
        jmsTemplate.setConnectionFactory(activeMQConnectionFactory);
        //此处可不设置默认，在发送消息时也可设置队列
        jmsTemplate.setDefaultDestination(queue);
        //客户端签收模式
        jmsTemplate.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        //设置事务
        jmsTemplate.setSessionTransacted(true);
        return jmsTemplate;
    }

    /**
     * 定义一个消息监听器连接工厂，这里定义的是点对点模式的监听器连接工厂
     *
     * @param activeMQConnectionFactory ActiveMQConnectionFactory
     * @return DefaultJmsListenerContainerFactory
     */
    @Bean(name = "jmsQueueListener")
    public DefaultJmsListenerContainerFactory jmsQueueListenerContainerFactory(ActiveMQConnectionFactory activeMQConnectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(activeMQConnectionFactory);
        //设置连接数
        factory.setConcurrency("1-10");
        //重连间隔时间
        factory.setRecoveryInterval(1000L);

//        factory.setSessionAcknowledgeMode(Session.CLIENT_ACKNOWLEDGE);
        //设置事务
        factory.setSessionTransacted(true);

        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        factory.setTransactionManager(jmsTransactionManager);
        return factory;
    }

    @Bean
    @Primary
    public Queue queue() {
        return new ActiveMQQueue("springboot.queue");
    }

    @Bean
    public Queue queue1() {
        return new ActiveMQQueue("springboot.queue1");
    }


    @Bean
    public JmsListenerContainerFactory jmsTopicListenerContainerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        //这里必须设置为true，false则表示是queue类型
        factory.setPubSubDomain(true);
        return factory;
    }

    @Bean
    public Topic topic() {
        return new ActiveMQTopic("springboot.topic");
    }
}
