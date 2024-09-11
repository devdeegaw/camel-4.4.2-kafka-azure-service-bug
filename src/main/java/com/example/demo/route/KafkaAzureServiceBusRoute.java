package com.example.demo.route;
import org.apache.camel.builder.endpoint.EndpointRouteBuilder;
import org.apache.camel.builder.endpoint.dsl.KafkaEndpointBuilderFactory;
import org.apache.camel.component.azure.servicebus.ServiceBusType;
import org.springframework.stereotype.Component;

@Component
public class KafkaAzureServiceBusRoute extends EndpointRouteBuilder{

    @Override
    public void configure() {
        //@formatter:off
        from(kafkaEndpointConsumerBuilder())
            .routeId("KafkaAzureServiceBusRoute")
            .log("message.received")
            .to(azureServicebus("order-notifications-order-state-inbound").serviceBusType(ServiceBusType.queue))
            .log("message.sent");
    }

    public KafkaEndpointBuilderFactory.KafkaEndpointConsumerBuilder kafkaEndpointConsumerBuilder() {

       return kafka("my-topic")
            .groupId("test.camel")
            .brokers("localhost:9092")
            .autoCommitEnable(true);
            //.allowManualCommit(true);
    }
}
