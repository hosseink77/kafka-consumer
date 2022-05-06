package tech.sobhan.kafkaconsumer.config;

import org.apache.camel.builder.AggregationStrategies;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ConsumerCamelConfig extends RouteBuilder {

    @Value("${consumer.start}")
    private String startPoint;

    @Value("${consumer.end}")
    private String endPoint;

    @Override
    public void configure() {

        from(startPoint).to("direct:start");

        from("direct:start")
                .aggregate(constant(true), AggregationStrategies.bean(Integer.class, "sum"))
                .completionInterval(60000)
                .setBody(simple ("Sum: ${body}"))
                .to(endPoint);
    }
}
