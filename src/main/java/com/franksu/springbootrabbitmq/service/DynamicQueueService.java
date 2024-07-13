package com.franksu.springbootrabbitmq.service;

import com.franksu.springbootrabbitmq.constant.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.service
 * @Author: suming9
 * @CreateTime: 2024-07-12  10:26
 * @Description: 消息队列管控服务类
 * @Version: 1.0
 */
@Service
@Slf4j
public class DynamicQueueService {
    private final ConnectionFactory connectionFactory;
    private final RabbitAdmin rabbitAdmin;
    private final SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory;
    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;
    private final FanoutExchange broadcastExchange;
    private final ConcurrentHashMap<String, SimpleMessageListenerContainer> listenerContainers = new ConcurrentHashMap<>();
    @Autowired
    private ApplicationContext applicationContext;
    @Value("${rabbitmq.listener.concurrency:5}")
    private int concurrency;

    public DynamicQueueService(ConnectionFactory connectionFactory,
                               @Qualifier("proxyRabbitAdmin") RabbitAdmin rabbitAdmin,
                               @Qualifier("proxyRabbitListenerContainerFactory") SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory,
                               RabbitTemplate rabbitTemplate,
                               TopicExchange exchange,
                               FanoutExchange broadcastExchange) {
        this.connectionFactory = connectionFactory;
        this.rabbitAdmin = rabbitAdmin;
        this.exchange = exchange;
        this.broadcastExchange = broadcastExchange;
        this.rabbitListenerContainerFactory = rabbitListenerContainerFactory;
        this.rabbitTemplate = rabbitTemplate;
    }

    /**
     * 判断队列是否存在
     * @param queueName 队列名称
     * @return
     */
    public boolean isQueueExists(String queueName) {
        return Boolean.TRUE.equals(rabbitTemplate.execute(channel -> {
            try {
                channel.queueDeclarePassive(queueName);
                return true;
            } catch (Exception e) {
                return false;
            }
        }));
    }

    /**
     * 创建队列并开启监听
     * @param queueName 队列名称
     * @param routingKey 路由键
     * @param handlerBeanName 消费beanName
     */
    public void createQueueAndStartListener(String queueName, String routingKey, String handlerBeanName) {
        // 创建持久化队列，如果队列已经存在则不会重复创建
        Queue queue = new Queue(queueName, true);
        if (!isQueueExists(queueName)) {
            rabbitAdmin.declareQueue(queue);
            log.info("Declared queue: " + queueName);

            // 绑定队列到交换器并指定路由键
            Binding binding = BindingBuilder.bind(queue).to(exchange).with(routingKey);
            rabbitAdmin.declareBinding(binding);
            log.info("Bound queue: " + queueName + " to exchange: " + exchange.getName() + " with routing key: " + routingKey);
        } else {
            log.info("Queue " + queueName + " already exists.");
        }
        // 若队列名称不是接收广播队列，则启动监听
        if (!queueName.equals(CommonConstants.BROADCAST_EXCHANGE)) {
            // 启动监听容器
            startListener(queueName, handlerBeanName);
        }
    }

    /**
     * 开启监听
     * @param queueName 队列名称
     * @param handlerBeanName 消费处理bean名称
     */
    public void startListener(String queueName, String handlerBeanName) {
        // 从 Spring 容器中获取 MessageHandler 实例
        Object messageHandler = applicationContext.getBean(handlerBeanName);
        // 创建消息监听容器
        SimpleMessageListenerContainer container = rabbitListenerContainerFactory.createListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        container.setMessageListener(new MessageListenerAdapter(messageHandler));

        // 设置其他参数（例如并发消费者数量，预取数量等）
        container.setConcurrentConsumers(concurrency);
        container.setPrefetchCount(5);
        container.setRecoveryInterval(5000L); // 自动重连间隔时间

        // 启动监听容器
        try {
            container.start();
            log.info("Started listener container for queue: " + queueName);
            // 检查容器是否正在运行
            if (container.isRunning()) {
                log.info("Listener container is running.");
            } else {
                log.warn("Listener container did not start properly.");
            }
        } catch (Exception e) {
            log.error("Failed to start listener container for queue: " + queueName, e);
            // 处理启动失败的情况
        }
        log.info("Started listener container for queue: " + queueName);

        // 存储监听容器，以便后续管理
        listenerContainers.put(queueName, container);
    }

    /**
     * 删除队列停止监听
     * @param queueName 队列名称
     */
    public void deleteQueueAndStopListener(String queueName) {
        // 停止监听容器
        SimpleMessageListenerContainer container = listenerContainers.get(queueName);
        if (container != null) {
            container.stop();
            listenerContainers.remove(queueName);
        }

        // 删除队列
        rabbitAdmin.deleteQueue(queueName);
    }

    /**
     * 广播消息
     * @param queueName 队列名
     * @param routingKey 路由键
     * @param isCreated 创建/删除 （true/false）
     */
    public void broadcastQueue(String queueName, String routingKey, boolean isCreated) {
        String message = queueName + ":" + routingKey + ":" + (isCreated ? CommonConstants.QUEUE_CREATE : CommonConstants.QUEUE_DELETE);
        rabbitTemplate.convertAndSend(broadcastExchange.getName(), routingKey, message);
        log.info("Broadcasted new queue creation: " + message);
    }
}