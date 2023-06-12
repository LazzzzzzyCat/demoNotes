package com.demo.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Queue;
import javax.jms.Topic;

/**
 * @author huwj
 * @date 2020-08-04 14:15
 */
@RestController
public class Producer {

    @Autowired
    private JmsMessagingTemplate jmsTemplate;

    @Autowired
    private Queue queue;

    @Autowired
    @Qualifier("queue1")
    private Queue queue1;

    @Autowired
    private Topic topic;

    @GetMapping("/queue")
    @Transactional(rollbackFor = Exception.class)
    public void sendQueueMsg(String msg) {
        jmsTemplate.convertAndSend(queue, msg);
        if(true){
            throw new RuntimeException("测试事务");
        }
    }

    @GetMapping("/queue1")
    public void sendQueue1Msg(String msg) {
        jmsTemplate.convertAndSend(queue1, msg);
    }


    @GetMapping("/topic")
    public void sendTopicMsg(String msg) {
        jmsTemplate.convertAndSend(topic, msg);
    }
}
