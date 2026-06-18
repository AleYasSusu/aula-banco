package br.com.conectateabackend.aulaawsjavards.service;

import br.com.conectateabackend.aulaawsjavards.domain.response.ViaCepResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ViaCepService {

    public ViaCepResponse consultarCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://viacep.com.br/ws/" + cep + "/json/";

        return restTemplate.getForObject(url, ViaCepResponse.class);
    }
}
