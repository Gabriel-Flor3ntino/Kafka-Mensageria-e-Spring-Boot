package br.com.gabrielflorentino.api_boleto.services;

import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;
import br.com.gabrielflorentino.api_boleto.entity.enums.SituacaoBoleto;
import br.com.gabrielflorentino.api_boleto.repository.BoletoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    public ? salvar(String codigoBarras, BoletoRepository boletoRepository) {
        var boletoOptional = boletoRepository.findByCodigoBarras(codigoBarras);

        if (boletoOptional.isPresent()) {
            // lançar uma exeption
            throw new RuntimeException("Já existe uma solicitação de pagamento para esse boleto");
        }
        var boletoEntity = BoletoEntity.builder()
                .codigoBarra(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        boletoRepository.save(boletoEntity);
    }
}
