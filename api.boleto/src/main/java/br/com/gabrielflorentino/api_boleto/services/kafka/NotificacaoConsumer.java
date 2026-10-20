package br.com.gabrielflorentino.api_boleto.services.kafka;

import br.com.gabrielflorentino.api_boleto.mapper.BoletoMapper;
import br.com.gabrielflorentino.api_boleto.services.BoletoService;
import br.com.gabrielflorentino.avro.Boleto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Service
public class NotificacaoConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(NotificacaoConsumer.class);

    private final BoletoService boletoService;

    public NotificacaoConsumer(BoletoService boletoService) {
        this.boletoService = boletoService;
    }

    @KafkaListener(topics =  "${spring.kafka.topico-notificacao}")
    public void consumer(@Payload Boleto boleto) {
        LOGGER.info(String.format("Consumindo notificacao -> %s", boleto));
        boletoService.atualizar(BoletoMapper.toEntity(boleto));
    }

}
