package br.com.conectateabackend.aulaawsjavards.service;


import br.com.conectateabackend.aulaawsjavards.domain.request.CepMensagemSqs;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class SqsProducerService {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    public void enviarMensagem(CepMensagemSqs mensagem) {
        try {
            String json = objectMapper.writeValueAsString(mensagem);

            SendMessageRequest request = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(json)
                    .build();

            sqsClient.sendMessage(request);

        } catch (Exception e) {
            throw new RuntimeException("Erro ao enviar mensagem para SQS", e);
        }
    }
}