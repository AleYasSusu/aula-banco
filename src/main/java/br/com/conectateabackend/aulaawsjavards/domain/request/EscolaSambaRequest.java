package br.com.conectateabackend.aulaawsjavards.domain.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EscolaSambaRequest {

    @NotBlank(message = "O nome da escola é obrigatório")
    private String nome;

    private String cidade;

    private String estado;

    private String presidente;

    private String cores;

    private Integer anoFundacao;
}