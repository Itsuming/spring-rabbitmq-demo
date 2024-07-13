package com.franksu.springbootrabbitmq.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.handler
 * @Author: suming9
 * @CreateTime: 2024-07-12  09:40
 * @Description: 用户消息处理器
 * @Version: 1.0
 */
@Component
@Slf4j
public class UserHandlerWithBytes implements MessageListener {
    @Override
    public void onMessage(Message message) {
        byte[] body = message.getBody();
        handleMessage(body);
    }

    public void handleMessage(byte[] content) {
        // 处理消息
        String message = new String(content, StandardCharsets.UTF_8);
        log.info("当前bytes消息格式为：" + message);
    }
}