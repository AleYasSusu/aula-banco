package br.com.conectateabackend.aulaawsjavards.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "escolas_samba")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EscolaSamba {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    private String cidade;

    private String estado;

    private String presidente;

    private String cores;

    private Integer anoFundacao;
}