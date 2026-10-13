package br.com.gabrielflorentino.api_boleto.services;

import br.com.gabrielflorentino.api_boleto.dto.BoletoDTO;
import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;
import br.com.gabrielflorentino.api_boleto.entity.enums.SituacaoBoleto;
import br.com.gabrielflorentino.api_boleto.mapper.BoletoMapper;
import br.com.gabrielflorentino.api_boleto.repository.BoletoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;

    public BoletoService(BoletoRepository boletoRepository) {
        this.boletoRepository = boletoRepository;
    }

    public BoletoDTO salvar(String codigoBarras) {
        var boletoOptional = boletoRepository.findByCodigoBarras(codigoBarras);
        if (boletoOptional.isPresent()) {
            // lançar uma exeption
            throw new RuntimeException("Já existe uma solicitação de pagamento para esse boleto");
        }

        var boletoEntity = BoletoEntity.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();

        boletoRepository.save(boletoEntity);

        return BoletoMapper.toDTO(boletoEntity);
    }

}
