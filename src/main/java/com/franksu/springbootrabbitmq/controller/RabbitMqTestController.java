package com.franksu.springbootrabbitmq.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.franksu.springbootrabbitmq.model.User;
import com.franksu.springbootrabbitmq.service.UserSendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.controller
 * @Author: suming9
 * @CreateTime: 2024-07-12  09:50
 * @Description: rabbitmq-controller
 * @Version: 1.0
 */
@RestController
@RequestMapping("/rabbitmq")
public class RabbitMqTestController {

    @Autowired
    private UserSendService userSendService;

    @PostMapping("/send")
    public void testSend() {
        User user = new User();
        user.setId(1);
        user.setName("frank");
        user.setAge(20);
        try {
            userSendService.sendWithMessage(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}