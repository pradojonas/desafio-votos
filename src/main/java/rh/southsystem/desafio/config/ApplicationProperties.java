package rh.southsystem.desafio.config;

import java.security.InvalidParameterException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("profile")
public class ApplicationProperties {
    
    // Class is not supposed to be static; Inject it with AutoWired
    
    // TODO: Create multiple profiles for database configuration

    @Value("${env:}")
    private String env;

    public String getEnv() {
        if (env.isBlank()) throw new InvalidParameterException("Error: 'env' variable is not defined in application.properties");
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }
}