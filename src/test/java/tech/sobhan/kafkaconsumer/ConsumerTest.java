package tech.sobhan.kafkaconsumer;

import org.apache.camel.EndpointInject;
import org.apache.camel.Produce;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.junit5.CamelSpringBootTest;
import org.apache.camel.test.spring.junit5.MockEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@CamelSpringBootTest
@MockEndpoints
public class ConsumerTest  {

    @EndpointInject("mock:stream:out")
    protected MockEndpoint mock;

    @Produce("direct:start")
    protected ProducerTemplate producer;


    @Test
    public void consumer_aggregateWithDelay() throws Exception {
        mock.expectedMessageCount(2);
        mock.expectedBodiesReceived("Sum: 10", "Sum: 5");

        int body = 1;
        for (; body < 5; body++)
            producer.sendBody(body);

        Thread.sleep(61000);
        producer.sendBody(body);

        mock.assertIsSatisfied();
    }

}
