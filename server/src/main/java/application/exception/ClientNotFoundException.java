package application.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClientNotFoundException extends RuntimeException {
    public ClientNotFoundException() {
        super();
    }

    public ClientNotFoundException(String msg) {
        super(msg);
    }

    public ClientNotFoundException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public ClientNotFoundException(Throwable throwable) {
        super(throwable);
    }

    protected ClientNotFoundException(String s, Throwable throwable, boolean b, boolean b1) {
        super(s, throwable, b, b1);
    }
}
