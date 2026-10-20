package br.com.gabrielflorentino.api_boleto.services;

import br.com.gabrielflorentino.api_boleto.controller.exception.ApplicationException;
import br.com.gabrielflorentino.api_boleto.controller.exception.NotFoundException;
import br.com.gabrielflorentino.api_boleto.dto.BoletoDTO;
import br.com.gabrielflorentino.api_boleto.entity.BoletoEntity;
import br.com.gabrielflorentino.api_boleto.entity.enums.SituacaoBoleto;
import br.com.gabrielflorentino.api_boleto.mapper.BoletoMapper;
import br.com.gabrielflorentino.api_boleto.repository.BoletoRepository;
import br.com.gabrielflorentino.api_boleto.services.kafka.BoletoProducer;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BoletoService {

    private final BoletoRepository boletoRepository;
    private final BoletoProducer boletoProducer;

    public BoletoService(BoletoRepository boletoRepository, BoletoProducer boletoProducer) {
        this.boletoRepository = boletoRepository;
        this.boletoProducer = boletoProducer;
    }

    public BoletoDTO salvar(String codigoBarras) {
        var boletoOptional = boletoRepository.findByCodigoBarras(codigoBarras);
        if (boletoOptional.isPresent()) {
            // lançar uma exeption
            throw new ApplicationException("Já existe uma solicitação de pagamento para esse boleto");
        }

        var boletoEntity = BoletoEntity.builder()
                .codigoBarras(codigoBarras)
                .situacaoBoleto(SituacaoBoleto.INICIALIZADO)
                .dataCriacao(LocalDateTime.now())
                .dataAtualizacao(LocalDateTime.now())
                .build();


        boletoRepository.save(boletoEntity);
        boletoProducer.enviarMensagem(BoletoMapper.toAvro(boletoEntity));
        return BoletoMapper.toDTO(boletoEntity);
    }

    public BoletoDTO buscarBoletoPorCodigoBarras(String codigoBarras) {
        return BoletoMapper.toDTO(recuperaBoleto((codigoBarras)));
    }

    private BoletoEntity recuperaBoleto(String codigoBarras) {
        return boletoRepository.findByCodigoBarras(codigoBarras).
                orElseThrow(() -> new NotFoundException("Boleto não encontrado"));
    }

    public void atualizar(BoletoEntity boleto) {
        var boletoAtual = recuperaBoleto(boleto.getCodigoBarras());

        boletoAtual.setSituacaoBoleto(boleto.getSituacaoBoleto());
        boletoAtual.setDataAtualizacao(LocalDateTime.now());
        boletoRepository.save(boletoAtual);
    }

}
