package rh.southsystem.desafio;

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

    public static void main(String[] args) {
        SpringApplication.run(DesafioVotosApplication.class, args);
        // TODO: Logger
    }

    @EventListener(ContextRefreshedEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println(String.format("Running Spring application in '%s' profile", appProps.getEnv()));
    }

}