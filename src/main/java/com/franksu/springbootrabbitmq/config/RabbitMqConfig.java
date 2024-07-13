package com.franksu.springbootrabbitmq.config;

import com.franksu.springbootrabbitmq.constant.CommonConstants;
import com.franksu.springbootrabbitmq.handler.UserHandlerWithMessage;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.autoconfigure.amqp.SimpleRabbitListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static com.franksu.springbootrabbitmq.constant.CommonConstants.*;

/**
 * @BelongsProject: springboot-rabbitmq
 * @BelongsPackage: com.franksu.springbootrabbitmq.config
 * @Author: suming9
 * @CreateTime: 2024-07-12  10:18
 * @Description: 配置类
 * @Version: 1.0
 */
@Configuration
public class RabbitMqConfig {
    @Bean
    @Primary
    public SimpleRabbitListenerContainerFactory proxyRabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            SimpleRabbitListenerContainerFactoryConfigurer configurer) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        configurer.configure(factory, connectionFactory);
        factory.setPrefetchCount(5); // 设置预取数量
        factory.setRecoveryInterval(5000L); // 自动重连间隔时间
        return factory;
    }

    @Bean
    public SimpleMessageListenerContainer userMessageListenerContainer(ConnectionFactory connectionFactory,
                                                                       MessageListenerAdapter userListenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(USER_QUEUE_NAME); // 设置要监听的队列名称
        container.setMessageListener(userListenerAdapter);
        container.setPrefetchCount(5);
        container.setRecoveryInterval(5000L);
        return container;
    }

    @Bean
    @Primary
    public RabbitAdmin proxyRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(CommonConstants.DYNAMIC_EXCHANGE, true, false);
    }

    @Bean
    public FanoutExchange broadcastExchange() {
        return new FanoutExchange(CommonConstants.BROADCAST_EXCHANGE, true, false);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    public Queue autoDeleteQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange broadcastExchange, Queue autoDeleteQueue) {
        return BindingBuilder.bind(autoDeleteQueue).to(broadcastExchange);
    }

    @Bean
    public MessageListenerAdapter userListenerAdapter(UserHandlerWithMessage handler) {
        return new MessageListenerAdapter(handler, "handleMessage");
    }

    //定义死信队列
    // 定义死信交换机
    @Bean
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    // 定义死信队列
    @Bean
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    // 绑定死信队列到死信交换机
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange()).with(DEAD_LETTER_ROUTING_KEY);
    }

    // 配置普通队列，绑定到死信交换机
    @Bean
    public Queue userQueue() {
        return QueueBuilder.durable(USER_QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE) // 设置死信交换机
                .withArgument("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY) // 设置死信路由键
                .build();
    }

}