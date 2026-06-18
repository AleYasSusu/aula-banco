package br.com.conectateabackend.aulaawsjavards.worker;

import br.com.conectateabackend.aulaawsjavards.domain.CepProcessamento;
import br.com.conectateabackend.aulaawsjavards.domain.enuns.StatusProcessamento;
import br.com.conectateabackend.aulaawsjavards.domain.request.CepMensagemSqs;
import br.com.conectateabackend.aulaawsjavards.domain.response.ViaCepResponse;
import br.com.conectateabackend.aulaawsjavards.repository.CepProcessamentoRepository;
import br.com.conectateabackend.aulaawsjavards.service.ViaCepService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class CepSqsWorker {

    private final SqsClient sqsClient;
    private final ObjectMapper objectMapper;
    private final ViaCepService viaCepService;
    private final CepProcessamentoRepository repository;

    @Value("${aws.sqs.queue-url}")
    private String queueUrl;

    @Scheduled(fixedDelay = 5000)
    public void processarMensagens() {
        ReceiveMessageRequest receiveRequest = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(5)
                .waitTimeSeconds(10)
                .build();

        List<Message> mensagens = sqsClient.receiveMessage(receiveRequest).messages();

        for (Message message : mensagens) {
            try {
                CepMensagemSqs cepMensagem = objectMapper.readValue(
                        message.body(),
                        CepMensagemSqs.class
                );

                processarCep(cepMensagem);

                deletarMensagem(message);

            } catch (Exception e) {
                System.out.println("Erro ao processar mensagem SQS: " + e.getMessage());
            }
        }
    }

    private void processarCep(CepMensagemSqs mensagem) {
        CepProcessamento processamento = repository.findById(mensagem.getProcessamentoId())
                .orElseThrow(() -> new RuntimeException("Processamento não encontrado"));

        try {
            processamento.setStatus(StatusProcessamento.PROCESSANDO);
            processamento.setAtualizadoEm(LocalDateTime.now());
            repository.save(processamento);

            ViaCepResponse viaCep = viaCepService.consultarCep(mensagem.getCep());

            if (viaCep == null || Boolean.TRUE.equals(viaCep.getErro())) {
                processamento.setStatus(StatusProcessamento.ERRO);
                processamento.setErro("CEP não encontrado");
            } else {
                processamento.setStatus(StatusProcessamento.PROCESSADO);
                processamento.setLogradouro(viaCep.getLogradouro());
                processamento.setComplemento(viaCep.getComplemento());
                processamento.setBairro(viaCep.getBairro());
                processamento.setLocalidade(viaCep.getLocalidade());
                processamento.setUf(viaCep.getUf());
                processamento.setErro(null);
            }

            processamento.setAtualizadoEm(LocalDateTime.now());
            repository.save(processamento);

        } catch (Exception e) {
            processamento.setStatus(StatusProcessamento.ERRO);
            processamento.setErro(e.getMessage());
            processamento.setAtualizadoEm(LocalDateTime.now());
            repository.save(processamento);
        }
    }

    private void deletarMensagem(Message message) {
        DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                .queueUrl(queueUrl)
                .receiptHandle(message.receiptHandle())
                .build();

        sqsClient.deleteMessage(deleteRequest);
    }
}