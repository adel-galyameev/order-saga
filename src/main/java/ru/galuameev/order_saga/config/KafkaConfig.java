package ru.galuameev.order_saga.config;

import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.kafka.autoconfigure.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import ru.galuameev.order_saga.saga.event.*;

@Configuration
public class KafkaConfig {

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, WithdrawEvent> billingEventKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        var delegate = new JsonDeserializer<>(WithdrawEvent.class);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new ErrorHandlingDeserializer<>(delegate));
        var factory = new ConcurrentKafkaListenerContainerFactory<String, WithdrawEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReserveProductsEvent> productEventKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        var delegate = new JsonDeserializer<>(ReserveProductsEvent.class);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new ErrorHandlingDeserializer<>(delegate));
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ReserveProductsEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CancelOrderEvent> cancelOrderEventKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        var delegate = new JsonDeserializer<>(CancelOrderEvent.class);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new ErrorHandlingDeserializer<>(delegate));
        var factory = new ConcurrentKafkaListenerContainerFactory<String, CancelOrderEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ReserveCourierEvent> reserveCourierEventKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        var delegate = new JsonDeserializer<>(ReserveCourierEvent.class);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new ErrorHandlingDeserializer<>(delegate));
        var factory = new ConcurrentKafkaListenerContainerFactory<String, ReserveCourierEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UnreserveProductsEvent> unreserveCourierEventKafkaListenerContainerFactory(
            KafkaProperties kafkaProperties) {
        var props = kafkaProperties.buildConsumerProperties();
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        var delegate = new JsonDeserializer<>(UnreserveProductsEvent.class);
        var consumerFactory = new DefaultKafkaConsumerFactory<>(
                props, new StringDeserializer(), new ErrorHandlingDeserializer<>(delegate));
        var factory = new ConcurrentKafkaListenerContainerFactory<String, UnreserveProductsEvent>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }
}
