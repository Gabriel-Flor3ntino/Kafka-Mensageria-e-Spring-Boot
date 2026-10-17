package br.com.gabrielflorentino.validador_boleto.service;

import br.com.gabrielflorentino.validador_boleto.entity.BoletoEntity;
import br.com.gabrielflorentino.validador_boleto.entity.enums.SituacaoBoleto;
import br.com.gabrielflorentino.validador_boleto.mapper.BoletoMapper;
import br.com.gabrielflorentino.validador_boleto.repository.BoletoRepository;
import br.com.gabrielflorentino.validador_boleto.service.kafka.NotificacaoProducer;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PagarBoletoService {

    private final BoletoRepository boletoRepository;
    private final NotificacaoProducer notificacaoProducer;

    public PagarBoletoService(BoletoRepository boletoRepository, NotificacaoProducer notificacaoProducer) {
        this.boletoRepository = boletoRepository;
        this.notificacaoProducer = notificacaoProducer;
    }

    @SneakyThrows
    public void pagar(BoletoEntity boleto) {
        Thread.sleep(1000);
        String codigoBarrasNumeros = boleto.getCodigoBarras().replaceAll("[0-9]", "");

        if (codigoBarrasNumeros.length() > 47) {
            complementarBoletoErro(boleto);
        } else {
            complementarBoletoSucesso(boleto);
        }

        boletoRepository.save(boleto);
        notificacaoProducer.enviarMensagem(BoletoMapper.toAvro(boleto));
    }

    private void complementarBoletoErro(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.ERRO_PAGAMENTO);
    }

    private void complementarBoletoSucesso(BoletoEntity boleto) {
        boleto.setDataAtualizacao(LocalDateTime.now());
        boleto.setSituacaoBoleto(SituacaoBoleto.PAGO);
    }
}
