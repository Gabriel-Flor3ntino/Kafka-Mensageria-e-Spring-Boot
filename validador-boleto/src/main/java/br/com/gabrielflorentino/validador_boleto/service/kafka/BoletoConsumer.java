package br.com.gabrielflorentino.validador_boleto.service.kafka;

import br.com.gabrielflorentino.avro.Boleto;
import org.apache.kafka.clients.consumer.internals.AcknowledgementBatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class BoletoConsumer {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(BoletoConsumer.class);

    @KafkaListener(
            topics = "${spring.kafka.topico-boleto}",
            groupId = "${spring.kafka.consumer.group-id}"
    )
    public void consomeBoleto(@Payload Boleto boleto, Acknowledgment ack)  {
        LOGGER.info("Consumindo mensagem -> {}", boleto);
        ack.acknowledge();
    }


}