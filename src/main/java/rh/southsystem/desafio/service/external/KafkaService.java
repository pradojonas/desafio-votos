package rh.southsystem.desafio.service.external;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.serialization.LongSerializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import rh.southsystem.desafio.config.ApplicationProperties;

@Service
public class KafkaService {

    @Autowired
    private ApplicationProperties appProps;
    
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public void sendKafkaMessage(Long idSession, String message) {
        try {
            // TODO: Configure Logger to hide Kafka configuration information
            var producer = new KafkaProducer<Long, String>(this.kafkaProperties());
            var record   = new ProducerRecord<>(appProps.getKafkaSessionTopic(), idSession, message);
            LOGGER.info(String.format("Sending to Kafka: %s", message));
            producer.send(record, (data, ex) -> {
                if (ex != null) {
                    return; // TODO: Check if theres a problem here
                }
                LOGGER.info(String.format("Message sent for topic %S at offset %s",
                                                 data.topic(),
                                                 data.offset()));
            }).get();
        } catch (KafkaException | InterruptedException | ExecutionException e) {
            LOGGER.info("Error sending message to Kafka");
        }
    }

    private Properties kafkaProperties() {
        var properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, appProps.getKafkaServerPath());
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
                               StringSerializer.class.getName());
        return properties;
    }

}
