package fr.kflamand.Backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException() {
        super();
    }

    public TokenExpiredException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public TokenExpiredException(final String message) {
        super(message);
    }

    public TokenExpiredException(final Throwable cause) {
        super(cause);
    }
}
