package tech.sobhan.kafkaconsumer.config;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ConsumerCamelConfig extends RouteBuilder {

    @Value("${consumer.start}")
    private String startPoint;

    @Value("${consumer.end}")
    private String endPoint;

    @Override
    public void configure() {
        from(startPoint)
                .aggregate(constant(true), AggregationStrategies.bean(Integer.class, "sum"))
                .completionInterval(60000)
                .setBody(simple ("Sum: ${body}"))
                .to(endPoint);
    }
}
