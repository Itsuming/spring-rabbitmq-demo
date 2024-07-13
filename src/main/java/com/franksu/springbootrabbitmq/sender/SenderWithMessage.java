package com.franksu.springbootrabbitmq.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.franksu.springbootrabbitmq.config.RabbitMqConfig;
import com.franksu.springbootrabbitmq.constant.CommonConstants;
import com.franksu.springbootrabbitmq.model.User;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;
import org.springframework.util.MimeTypeUtils;

import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.sender
 * @Author: suming9
 * @CreateTime: 2024-07-12  10:53
 * @Description: 基于Message发送消息
 * @Version: 1.0
 */
@Component
public class SenderWithMessage {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    // 发送消息
    public void sendMsg(User user) throws JsonProcessingException {
        // Message<String> message = MessageBuilder.withPayload(objectMapper.writeValueAsString(user))
        //         .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
        //         .build();

        String jsonString = objectMapper.writeValueAsString(user);
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setContentType(MessageProperties.CONTENT_TYPE_JSON);
        messageProperties.setContentEncoding(StandardCharsets.UTF_8.name());

        Message message = MessageBuilder.withBody(jsonString.getBytes(StandardCharsets.UTF_8))
                .andProperties(messageProperties)
                .build();
        rabbitTemplate.convertAndSend(CommonConstants.DYNAMIC_EXCHANGE, CommonConstants.DYNAMIC_ROUTINGKEY, message);
    }
}