package br.com.gabrielflorentino.api_boleto.mapper;

import br.com.gabrielflorentino.api_boleto.dto.BoletoDTO;
import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;

public class BoletoMapper {

    public static BoletoDTO toDTO(BoletoEntity boleto) {
        return BoletoDTO.builder()
                .codigoBarra(boleto.getCodigoBarras())
                .situacaoBoleto(boleto.getSituacaoBoleto())
                .dataCriacao(boleto.getDataCriacao())
                .dataAtualizacao(boleto.getDataAtualizacao())
                .build();
    }
}
