package br.com.conectateabackend.aulaawsjavards.domain;

import br.com.conectateabackend.aulaawsjavards.domain.enuns.StatusProcessamento;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "cep_processamentos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CepProcessamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    @Enumerated(EnumType.STRING)
    private StatusProcessamento status;

    private String logradouro;

    private String complemento;

    private String bairro;

    private String localidade;

    private String uf;

    private String erro;

    private LocalDateTime criadoEm;

    private LocalDateTime atualizadoEm;
}
