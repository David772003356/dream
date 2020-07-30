package com.bigdream.dream.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-30 17:44
 **/
@Component
public class TopicConsumerListener {
    //topic模式的消费者
    @JmsListener(destination="${spring.activemq.topic-name}", containerFactory="topicListener")
    public void readActiveQueue(String message) {
        System.out.println("topic接受到：" + message);
    }
}
