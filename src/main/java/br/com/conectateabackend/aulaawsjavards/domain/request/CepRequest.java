package br.com.conectateabackend.aulaawsjavards.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CepRequest {

    @NotBlank(message = "O CEP é obrigatório")
    private String cep;
}
