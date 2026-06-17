package br.com.conectateabackend.aulaawsjavards.controler;

import br.com.conectateabackend.aulaawsjavards.domain.EscolaSamba;
import br.com.conectateabackend.aulaawsjavards.domain.request.EscolaSambaRequest;
import br.com.conectateabackend.aulaawsjavards.service.EscolaSambaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/escolas")
@RequiredArgsConstructor
public class EscolaSambaController {

    private final EscolaSambaService escolaSambaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EscolaSamba criar(@RequestBody @Valid EscolaSambaRequest request) {
        return escolaSambaService.criar(request);
    }

    @GetMapping
    public List<EscolaSamba> listarTodas() {
        return escolaSambaService.listarTodas();
    }

    @GetMapping("/{id}")
    public EscolaSamba buscarPorId(@PathVariable Long id) {
        return escolaSambaService.buscarPorId(id);
    }
}
