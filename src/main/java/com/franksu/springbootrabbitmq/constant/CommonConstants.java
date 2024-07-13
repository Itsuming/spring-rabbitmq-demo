package com.franksu.springbootrabbitmq.constant;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.constant
 * @Author: suming9
 * @CreateTime: 2024-07-12  10:19
 * @Description: 常量类
 * @Version: 1.0
 */
public class CommonConstants {

    /**
     * 定义exchange
     */
    public static final String DYNAMIC_EXCHANGE = "dynamic_exchange";
    /**
     * 定义广播交换机
     */
    public static final String BROADCAST_EXCHANGE = "broadcast_exchange";
    /**
     * 定义routingkey
     */
    public static final String DYNAMIC_ROUTINGKEY = "dynamic_routingkey";
    /**
     * 定义用户队列
     */
    public static final String USER_QUEUE_NAME = "user_frank";
    /**
     * 队列删除标志
     */
    public static final String QUEUE_DELETE = "delete";
    /**
     * 队列创建标志
     */
    public static final String QUEUE_CREATE = "create";
    /**
     * 死信队列交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "deadLetterExchange";
    /**
     * 死信队列routingkey
     */
    public static final String DEAD_LETTER_ROUTING_KEY = "deadLetter";
    /**
     * 死信队列名
     */
    public static final String DEAD_LETTER_QUEUE = "deadLetterQueue";
}