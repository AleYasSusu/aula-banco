package br.com.conectateabackend.aulaawsjavards.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;
import software.amazon.awssdk.services.sns.model.SubscribeResponse;

@Service
public class SnsService {

    private final SnsClient snsClient;

    @Value("${aws.sns.topic-arn}")
    private String topicArn;

    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public String publish(String message) {
        PublishRequest request = PublishRequest.builder()
                .topicArn(topicArn)
                .message(message)
                .subject("Mensagem SNS")
                .build();

        return snsClient.publish(request).messageId();
    }

    public String subscribeEmail(String email) {
        SubscribeRequest request = SubscribeRequest.builder()
                .topicArn(topicArn)
                .protocol("email")
                .endpoint(email)
                .build();

        SubscribeResponse response = snsClient.subscribe(request);
        return response.subscriptionArn();
    }

    public String subscribeHttp(String endpointUrl) {
        SubscribeRequest request = SubscribeRequest.builder()
                .topicArn(topicArn)
                .protocol("http")
                .endpoint(endpointUrl)
                .build();

        SubscribeResponse response = snsClient.subscribe(request);
        return response.subscriptionArn();
    }
}