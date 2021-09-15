package rh.southsystem.desafio.exceptions;

import org.springframework.http.HttpStatus;

public class DesafioException extends Exception {
    private static final long serialVersionUID = 1L;
    
    private HttpStatus statusCode;

    public DesafioException(String string, HttpStatus statusCode) {
        super(string);
        this.statusCode = statusCode;
    }

    public HttpStatus getStatusCode() {
        return this.statusCode;
    }

}
