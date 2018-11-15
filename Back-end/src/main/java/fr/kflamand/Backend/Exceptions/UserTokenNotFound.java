package fr.kflamand.Backend.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserTokenNotFound extends RuntimeException {

    public UserTokenNotFound() {
        super();
    }

    public UserTokenNotFound(final String message, final Throwable cause) {
        super(message, cause);
    }

    public UserTokenNotFound(final String message) {
        super(message);
    }

    public UserTokenNotFound(final Throwable cause) {
        super(cause);
    }

}