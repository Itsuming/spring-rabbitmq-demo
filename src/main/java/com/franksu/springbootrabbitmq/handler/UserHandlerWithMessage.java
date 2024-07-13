package com.franksu.springbootrabbitmq.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.franksu.springbootrabbitmq.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.handler
 * @Author: suming9
 * @CreateTime: 2024-07-12  11:02
 * @Description: 消息为Message的监听处理
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserHandlerWithMessage {

    @Autowired
    private ObjectMapper objectMapper;
    public void handleMessage(byte[] message) throws MessagingException {
        try {
            String messageContent = new String(message, StandardCharsets.UTF_8);

            // 将消息内容反序列化为 User 对象
            User user = objectMapper.readValue(messageContent, User.class);

            // 处理消息的逻辑
            log.info("当前Message格式消息为：" + user.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}