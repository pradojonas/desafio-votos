package rh.southsystem.desafio.config;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
public class ApplicationProperties {

    // Class is not supposed to be static; Inject it with AutoWired

    // TODO: Create multiple profiles for database configuration

    // TODO: Configure Swagger?

    @Value("${profile.env}")
    private String env;

    @Value("${session.duration.seconds}")
    private Long sessionDuration; // Value in seconds

    public String getEnv() {
        if (env.isBlank())
            throw new InvalidParameterException("Error: 'env' variable is not defined in application.properties");
        return env;
    }

    public Long getSessionDuration() {
        return sessionDuration;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setSessionDuration(Long sessionDuration) {
        this.sessionDuration = sessionDuration;
    }
}