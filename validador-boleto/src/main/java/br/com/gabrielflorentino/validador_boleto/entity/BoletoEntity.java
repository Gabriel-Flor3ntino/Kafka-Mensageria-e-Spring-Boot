package br.com.gabrielflorentino.validador_boleto.entity;

import br.com.gabrielflorentino.validador_boleto.entity.enums.SituacaoBoleto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_barra")
    private String codigoBarras;

    @Column(name = "situacao_boleto")
    private SituacaoBoleto situacaoBoleto;

    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Column(name = "data_autalizacao")
    private LocalDateTime dataAtualizacao;


}
