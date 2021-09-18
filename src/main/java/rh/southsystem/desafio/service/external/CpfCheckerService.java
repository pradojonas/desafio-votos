package rh.southsystem.desafio.service.external;

import java.time.Duration;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import reactor.core.Exceptions;
import reactor.core.publisher.Mono;
import rh.southsystem.desafio.config.ApplicationProperties;
import rh.southsystem.desafio.dto.CpfApiDTO;
import rh.southsystem.desafio.enums.CanVoteEnum;
import rh.southsystem.desafio.exceptions.MappedException;

@Service
public class CpfCheckerService {
    
    @Autowired
    private ApplicationProperties   appProps;
    
    public void validateCpfUsingAPI(String cpf) throws MappedException {
        String urlWithParameters = appProps.getCpfApiUrl().replace("{cpf}", cpf);

        try {
            WebClient webClient = WebClient.builder()
                                           .baseUrl(urlWithParameters)
                                           .defaultHeader(HttpHeaders.CONTENT_TYPE,
                                                          MediaType.APPLICATION_JSON_VALUE)
                                           .build();
            CpfApiDTO result    = webClient.get()
                                           .retrieve()
                                           .onStatus(obtainedCode -> obtainedCode.equals(HttpStatus.NOT_FOUND),
                                                     request -> Mono.error(new MappedException(String.format("The CPF '%s' is invalid.",
                                                                                                             cpf),
                                                                                               HttpStatus.BAD_REQUEST)))
                                           .bodyToMono(CpfApiDTO.class)
                                           .timeout(Duration.ofSeconds(appProps.getCpfApiTimeout()))
                                           .block();
            System.out.println(cpf + ": " + result.getStatus());
            // TODO: Replace prints with logger
            if (result.getStatus() != CanVoteEnum.ABLE_TO_VOTE)
                throw new MappedException(String.format("This associate (CPF = %s) is unable to vote.", cpf),
                                          HttpStatus.FORBIDDEN);
        } catch (RuntimeException e) {
            // Thrown by 'block()'
            var cause = Exceptions.unwrap(e);
            if (cause instanceof WebClientRequestException
                || cause instanceof TimeoutException)
                throw new MappedException(String.format("The CPF API (%s) is unavailable. Please, try again later.",
                                                        urlWithParameters), HttpStatus.SERVICE_UNAVAILABLE);
            throw e;
        }
    }

}
