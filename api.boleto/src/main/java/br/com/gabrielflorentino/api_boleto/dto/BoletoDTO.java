package br.com.gabrielflorentino.api_boleto.dto;

import br.com.gabrielflorentino.api_boleto.entity.enums.SituacaoBoleto;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BoletoDTO {

    private String codigoBarra;
    private SituacaoBoleto situacaoBoleto;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataAtualizacao;
}
