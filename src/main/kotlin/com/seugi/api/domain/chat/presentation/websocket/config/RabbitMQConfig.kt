package com.seugi.api.domain.chat.presentation.websocket.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.annotation.EnableRabbit
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableRabbit
class RabbitMQConfig(
    @Value("\${spring.rabbitmq.host:}") private val rabbitmqHost: String,
    @Value("\${spring.rabbitmq.username:}") private val username: String,
    @Value("\${spring.rabbitmq.password:}") private val pwd: String
) {

    companion object {
        const val CHAT_QUEUE_NAME = "chat.queue"
        const val CHAT_EXCHANGE_NAME = "chat.exchange"
        const val ROUTING_KEY = "*.room.*"
    }

    @Bean
    fun queue() = Queue(CHAT_QUEUE_NAME, true)

    @Bean
    fun exchange() = TopicExchange(CHAT_EXCHANGE_NAME)

    @Bean
    fun binding(queue: Queue, exchange: TopicExchange) = BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY)

    @Bean
    fun rabbitTemplate() : RabbitTemplate {
        val rabbitTemplate = RabbitTemplate(connectionFactory())
        rabbitTemplate.messageConverter = jsonMessageConverter()
        return rabbitTemplate
    }

    @Bean
    fun connectionFactory() : CachingConnectionFactory {
        val factory = CachingConnectionFactory()
        factory.setHost(rabbitmqHost)
        factory.username = username
        factory.setPassword(pwd)
        return factory
    }

    @Bean
    fun jsonMessageConverter() = Jackson2JsonMessageConverter()
}