package br.com.conectateabackend.aulaawsjavards.service;

import br.com.conectateabackend.aulaawsjavards.domain.CepProcessamento;
import br.com.conectateabackend.aulaawsjavards.domain.enuns.StatusProcessamento;
import br.com.conectateabackend.aulaawsjavards.domain.request.CepMensagemSqs;
import br.com.conectateabackend.aulaawsjavards.domain.request.CepRequest;

import br.com.conectateabackend.aulaawsjavards.repository.CepProcessamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CepService {

    private final CepProcessamentoRepository repository;
    private final SqsProducerService sqsProducerService;

    public Long solicitarProcessamento(CepRequest request) {
        String cepLimpo = request.getCep().replaceAll("\\D", "");

        CepProcessamento processamento = CepProcessamento.builder()
                .cep(cepLimpo)
                .status(StatusProcessamento.PENDENTE)
                .criadoEm(LocalDateTime.now())
                .atualizadoEm(LocalDateTime.now())
                .build();

        processamento = repository.save(processamento);

        CepMensagemSqs mensagem = CepMensagemSqs.builder()
                .processamentoId(processamento.getId())
                .cep(cepLimpo)
                .build();

        sqsProducerService.enviarMensagem(mensagem);

        return processamento.getId();
    }

    public CepProcessamento buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Processamento não encontrado"));
    }
}
