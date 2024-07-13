package com.franksu.springbootrabbitmq.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franksu.springbootrabbitmq.constant.CommonConstants;
import com.franksu.springbootrabbitmq.handler.UserHandlerWithBytes;
import com.franksu.springbootrabbitmq.handler.UserHandlerWithMessage;
import com.franksu.springbootrabbitmq.model.User;
import com.franksu.springbootrabbitmq.sender.SenderWithBytes;
import com.franksu.springbootrabbitmq.sender.SenderWithMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.service
 * @Author: suming9
 * @CreateTime: 2024-07-12  10:33
 * @Description: 用户消息发送service
 * @Version: 1.0
 */
@Service
public class UserSendService {

    @Autowired
    private DynamicQueueService dynamicQueueService;
    @Autowired
    private ApplicationContext applicationContext;
    @Autowired
    private SenderWithBytes senderWithBytes;
    @Autowired
    private SenderWithMessage senderWithMessage;
    public void sendWithBytes(User user) throws JsonProcessingException {
        String messageHandlerBeanName = applicationContext.getBeansOfType(UserHandlerWithBytes.class).keySet().stream().collect(Collectors.toList()).get(0);
        // 创建队列
        dynamicQueueService.createQueueAndStartListener(CommonConstants.USER_QUEUE_NAME, CommonConstants.DYNAMIC_ROUTINGKEY, messageHandlerBeanName);
        senderWithBytes.sendMsg(user);
    }

    public void sendWithMessage(User user) throws JsonProcessingException {
        String messageHandlerBeanName = applicationContext.getBeansOfType(UserHandlerWithMessage.class).keySet().stream().collect(Collectors.toList()).get(0);
        // 创建队列
        dynamicQueueService.createQueueAndStartListener(CommonConstants.USER_QUEUE_NAME, CommonConstants.DYNAMIC_ROUTINGKEY, messageHandlerBeanName);
        senderWithMessage.sendMsg(user);
    }
}