package rh.southsystem.desafio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;

import rh.southsystem.desafio.config.ApplicationProperties;

@SpringBootApplication
public class DesafioVotosApplication {

    @Autowired
    ApplicationProperties appProps;

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    public static void main(String[] args) {
        SpringApplication.run(DesafioVotosApplication.class, args);
    }

    @EventListener(ContextRefreshedEvent.class)
    public void doSomethingAfterStartup() {
        LOGGER.info(String.format("Running Spring application in '%s' profile", appProps.getEnv()));
    }

}