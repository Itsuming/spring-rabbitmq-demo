package com.franksu.springbootrabbitmq.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.franksu.springbootrabbitmq.constant.CommonConstants;
import com.franksu.springbootrabbitmq.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.sender
 * @Author: suming9
 * @CreateTime: 2024-07-12  09:19
 * @Description: 基于Bytes发送消息
 * @Version: 1.0
 */
@Component
public class SenderWithBytes {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 发送消息
    public void sendMsg(User user) {
        ObjectNode objectNode = objectMapper.createObjectNode();
        objectNode.set("user", objectMapper.valueToTree(user));
        rabbitTemplate.convertAndSend(CommonConstants.DYNAMIC_EXCHANGE, CommonConstants.DYNAMIC_ROUTINGKEY, objectNode);
    }
}