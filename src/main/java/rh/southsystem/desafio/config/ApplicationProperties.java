package rh.southsystem.desafio.config;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {

    // Class is not supposed to be static; Inject it with AutoWired

    // TODO: Create multiple profiles for database configuration

    // TODO: Configure Swagger?

    @Value("${profile.env}")
    private String env;

    @Value("${session.duration.seconds}")
    private Long sessionDurationSeconds; // Value in seconds

    @Value("${api.valid.vote.url}")
    private String cpfApiUrl; // Replace {cpf} for parameter

    public String getEnv() {
        if (env.isBlank())
            throw new InvalidParameterException("Error: 'profile.env' variable is not defined in application.properties");
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getCpfApiUrl() {
        if (cpfApiUrl.isBlank())
            throw new InvalidParameterException("Error: 'cpfApiUrl' variable is not defined in application.properties");
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
}