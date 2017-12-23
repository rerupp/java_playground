package spring.microservices.common;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@SuppressWarnings("JavaDoc")
@ResponseStatus(HttpStatus.NOT_FOUND)
public class AccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1695153381216145123L;

    public AccountNotFoundException(final String account) {
        super("No such account: " + account);
    }
}
