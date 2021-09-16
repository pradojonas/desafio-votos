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

    public String getEnv() {
        if (env.isBlank())
            throw new InvalidParameterException("Error: 'env' variable is not defined in application.properties");
        return env;
    }

    public Long getSessionDurationSeconds() {
        return sessionDurationSeconds;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public void setSessionDurationSeconds(Long sessionDuration) {
        this.sessionDurationSeconds = sessionDuration;
    }
}