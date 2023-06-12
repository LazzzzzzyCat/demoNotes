package com.demo.datatype.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * activeMq消费者demo
 * @author huwj
 * @date 2020-04-08 09:04
 */
public class ActiveMqConsumerDemo {
    private static final String ACTIVEMQ_URL = "tcp://localhost:61616";

    public static void main(String[] args) throws JMSException {

        ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);

        Connection connection = activeMQConnectionFactory.createConnection();

        connection.start();

        final Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        Destination myQueue = session.createQueue("myQueue");
        MessageConsumer consumer = session.createConsumer(myQueue);

        consumer.setMessageListener(new MessageListener() {
            public void onMessage(Message message) {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println("消费的消息："+textMessage.getText());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
