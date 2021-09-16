package rh.southsystem.desafio;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.SpringVersion;

@SpringBootApplication
public class DesafioVotosApplication {

    public static void main(String[] args) {
        SpringApplication.run(DesafioVotosApplication.class, args);
    }
    
    @EventListener(ContextRefreshedEvent.class)
    public void doSomethingAfterStartup() {
        System.out.println(String.format("Running Spring application in %s version", SpringVersion.getVersion()));
    }    

}