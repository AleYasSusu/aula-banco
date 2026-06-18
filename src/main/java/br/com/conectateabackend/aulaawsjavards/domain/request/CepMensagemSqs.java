package br.com.conectateabackend.aulaawsjavards.domain.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CepMensagemSqs {

    private Long processamentoId;
    private String cep;
}
