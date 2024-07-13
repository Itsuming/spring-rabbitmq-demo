package com.franksu.springbootrabbitmq.model;

import lombok.Data;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.model
 * @Author: suming9
 * @CreateTime: 2024-07-12  09:21
 * @Description: 用户模型
 * @Version: 1.0
 */
@Data
public class User {
    private int id;
    private String name;
    private int age;

}