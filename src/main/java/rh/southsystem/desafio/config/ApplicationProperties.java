package rh.southsystem.desafio.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySources({ @PropertySource(value = "classpath:/app-${spring.profiles.active}.properties",
                                   ignoreResourceNotFound = false) })
public class ApplicationProperties {

    @Autowired
    Environment env;

    @Value("${session.duration.seconds}")
    private Long sessionDurationSeconds; // Value in seconds

    @Value("${api.valid.vote.url}")
    private String cpfApiUrl; // Replace {cpf} for parameter

    @Value("${api.valid.vote.timeout.seconds}")
    private Long cpfApiTimeout;

    @Value("${kafka.path.server}")
    private String kafkaServerPath;

    @Value("${kafka.session.topic}")
    private String kafkaSessionTopic;

    public String getKafkaSessionTopic() {
        return kafkaSessionTopic;
    }

    public void setKafkaSessionTopic(String kafkaSessionTopic) {
        this.kafkaSessionTopic = kafkaSessionTopic;
    }

    public List<String> getEnv() {
        return Arrays.asList(env.getActiveProfiles());
    }

    public Long getSessionDurationSeconds() {
        return sessionDurationSeconds;
    }

    public void setSessionDurationSeconds(Long sessionDurationSeconds) {
        this.sessionDurationSeconds = sessionDurationSeconds;
    }

    public String getCpfApiUrl() {
        return cpfApiUrl;
    }

    public void setCpfApiUrl(String cpfApiUrl) {
        this.cpfApiUrl = cpfApiUrl;
    }

    public Long getCpfApiTimeout() {
        return cpfApiTimeout;
    }

    public void setCpfApiTimeout(Long cpfApiTimeout) {
        this.cpfApiTimeout = cpfApiTimeout;
    }

    public String getKafkaServerPath() {
        return kafkaServerPath;
    }

    public void setKafkaServerPath(String kafkaServerPath) {
        this.kafkaServerPath = kafkaServerPath;
    }
}