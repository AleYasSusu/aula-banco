package br.com.conectateabackend.aulaawsjavards.service;

import br.com.conectateabackend.aulaawsjavards.domain.EscolaSamba;
import br.com.conectateabackend.aulaawsjavards.domain.request.EscolaSambaRequest;
import br.com.conectateabackend.aulaawsjavards.repository.EscolaSambaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EscolaSambaService {

    private final EscolaSambaRepository escolaSambaRepository;

    public EscolaSamba criar(EscolaSambaRequest request) {
        EscolaSamba escola = EscolaSamba.builder()
                .nome(request.getNome())
                .cidade(request.getCidade())
                .estado(request.getEstado())
                .presidente(request.getPresidente())
                .cores(request.getCores())
                .anoFundacao(request.getAnoFundacao())
                .build();

        return escolaSambaRepository.save(escola);
    }

    public List<EscolaSamba> listarTodas() {
        return escolaSambaRepository.findAll();
    }

    public EscolaSamba buscarPorId(Long id) {
        return escolaSambaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Escola de samba não encontrada"));
    }
}
