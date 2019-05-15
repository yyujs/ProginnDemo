package com.amqptest.rabbit.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;

/**
 * @Author Yujs
 * @DATE 2019-03-21 17:03
 **/
@Service
public class HelloSender implements RabbitTemplate.ReturnCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void send() {
        String context = "你好现在是" + LocalDateTime.now() + "";
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback((correlationData,ack,cause)->{
            if (!ack) {
                System.out.println("HelloSender消息发送失败"+cause + correlationData.toString());
            }else {
                System.out.println("消息发送成功");
            }
        });
        this.rabbitTemplate.convertAndSend("hello",context);
    }

    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("sender return success" + message.toString()+"==="+i+"==="+s1+"==="+s2);
    }
}
