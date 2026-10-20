package br.com.gabrielflorentino.api_boleto.mapper;

import br.com.gabrielflorentino.api_boleto.entity.enums.SituacaoBoleto;
import br.com.gabrielflorentino.avro.Boleto;
import br.com.gabrielflorentino.api_boleto.dto.BoletoDTO;
import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;

public class BoletoMapper {

    public static BoletoDTO toDTO(BoletoEntity boleto) {
        return BoletoDTO.builder()
                .codigoBarras(boleto.getCodigoBarras())
                .situacaoBoleto(boleto.getSituacaoBoleto())
                .dataCriacao(boleto.getDataCriacao())
                .dataAtualizacao(boleto.getDataAtualizacao())
                .build();
    }

    public static Boleto toAvro(BoletoEntity boleto) {
        return Boleto.newBuilder()
                .setCodigoBarras(boleto.getCodigoBarras())
                .setSituacaoBoleto(boleto.getSituacaoBoleto().ordinal())
                .build();
    }

    public static BoletoEntity toEntity(Boleto boleto) {
        return BoletoEntity.builder()
                .codigoBarras(boleto.getCodigoBarras().toString())
                .situacaoBoleto(SituacaoBoleto.values()[boleto.getSituacaoBoleto()])
                .build();
    }
}
