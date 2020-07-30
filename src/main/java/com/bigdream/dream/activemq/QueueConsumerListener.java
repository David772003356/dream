package com.bigdream.dream.activemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: Wu Yuwei
 * @create: 2020-07-30 17:43
 **/
@Component
public class QueueConsumerListener {
    //queue模式的消费者
    @JmsListener(destination="${spring.activemq.queue-name}", containerFactory="queueListener")
    public void readActiveQueue(String message) {
        System.out.println("queue接受到：" + message);
    }
}
