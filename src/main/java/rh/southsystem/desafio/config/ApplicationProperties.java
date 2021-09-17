package rh.southsystem.desafio.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@PropertySource(value = "classpath:/app-${spring.profiles.active}.properties", ignoreResourceNotFound = false)
public class ApplicationProperties {

    // TODO: Configure Swagger

    @Value("${profile.env}")
    private String env;

    @Value("${session.duration.seconds}")
    private Long sessionDurationSeconds; // Value in seconds

    @Value("${api.valid.vote.url}")
    private String cpfApiUrl; // Replace {cpf} for parameter

    @Value("${api.valid.vote.timeout.seconds}")
    private Long cpfApiTimeout;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getCpfApiUrl() {
        return cpfApiUrl;
    }

    public void setCpfApiUrl(String cpfApiUrl) {
        this.cpfApiUrl = cpfApiUrl;
    }

    public Long getSessionDurationSeconds() {
        return sessionDurationSeconds;
    }

    public void setSessionDurationSeconds(Long sessionDuration) {
        this.sessionDurationSeconds = sessionDuration;
    }

    public Long getCpfApiTimeout() {
        return cpfApiTimeout;
    }
}