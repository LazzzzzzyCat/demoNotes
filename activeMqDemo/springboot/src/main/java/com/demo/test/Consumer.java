package com.demo.test;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

/**
 * 消费者
 * @author huwj
 * @date 2020-08-04 14:16
 */
@Component
public class Consumer {

    /**
     * destination对应配置类中ActiveMQQueue("springboot.queue")设置的名字
     *
     * @param msg
     */
    @JmsListener(destination = "springboot.queue", containerFactory = "jmsQueueListener")
    public void listenQueue(TextMessage msg, Session session) throws JMSException {
        String text = msg.getText();
        System.out.println("consumer0接收到queue消息：" + text);
//        msg.acknowledge();
//        session.recover();
    }

    @JmsListener(destination = "springboot.queue", containerFactory = "jmsQueueListener")
    public void listenQueue1(TextMessage msg, Session session) throws JMSException {
        System.out.println("consumer1接收到queue消息：" + msg.getText());

    }

    /**
     * destination对应配置类中ActiveMQTopic("springboot.topic")设置的名字
     * containerFactory对应配置类中注册JmsListenerContainerFactory的bean名称
     *
     * @param msg
     */
    @JmsListener(destination = "springboot.topic", containerFactory = "jmsTopicListenerContainerFactory")
    public void listenTopic(String msg) {
        System.out.println("接收到topic消息：" + msg);
    }
}
