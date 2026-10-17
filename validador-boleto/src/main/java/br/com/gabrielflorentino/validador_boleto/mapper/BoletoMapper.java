package br.com.gabrielflorentino.validador_boleto.mapper;

import br.com.gabrielflorentino.avro.Boleto;
import br.com.gabrielflorentino.validador_boleto.entity.BoletoEntity;
import br.com.gabrielflorentino.validador_boleto.entity.enums.SituacaoBoleto;

public class BoletoMapper {

    public static BoletoEntity toEntity(Boleto boleto) {
        return BoletoEntity.builder()
                .codigoBarras(boleto.getCodigoBarras().toString())
                .situacaoBoleto(SituacaoBoleto.values()[boleto.getSituacaoBoleto()])
                .build();
    }

    public static Boleto toAvro(BoletoEntity boleto) {
        return Boleto.newBuilder()
                .setCodigoBarras(boleto.getCodigoBarras())
                .setSituacaoBoleto(boleto.getSituacaoBoleto().ordinal())
                .build();
    }
}
