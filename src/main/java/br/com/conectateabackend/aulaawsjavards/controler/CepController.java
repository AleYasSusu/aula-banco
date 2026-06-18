package br.com.conectateabackend.aulaawsjavards.controler;


import br.com.conectateabackend.aulaawsjavards.domain.CepProcessamento;
import br.com.conectateabackend.aulaawsjavards.domain.request.CepRequest;
import br.com.conectateabackend.aulaawsjavards.service.CepService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ceps")
@RequiredArgsConstructor
public class CepController {

    private final CepService cepService;

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Map<String, Object> solicitarProcessamento(@RequestBody @Valid CepRequest request) {
        Long id = cepService.solicitarProcessamento(request);

        return Map.of(
                "id", id,
                "mensagem", "CEP enviado para processamento"
        );
    }

    @GetMapping("/{id}")
    public CepProcessamento buscarProcessamento(@PathVariable Long id) {
        return cepService.buscarPorId(id);
    }
}
