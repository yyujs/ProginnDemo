package com.amqptest.rabbit;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * @Author Yujs
 * @DATE 2019-03-21 17:36
 **/
@RestController
@RequestMapping("/rabbit")
public class SendController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    /**
     *  测试广播模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/fanout")
    public ResponseEntity send(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());

        rabbitTemplate.convertAndSend("FANOUT_EXCHANGE", "", p, correlationData);
        return ResponseEntity.ok().build();
    }

    /**
     *  测试Direct模式.
     *
     * @param p the p
     * @return the response entity
     */
    @RequestMapping("/direct")
    public ResponseEntity direct(String p) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        System.out.println(correlationData);
        rabbitTemplate.convertAndSend("DIRECT_EXCHANGE", "DIRECT_ROUTING_KEY", "hello world", correlationData);
        return ResponseEntity.ok().build();
    }
}
