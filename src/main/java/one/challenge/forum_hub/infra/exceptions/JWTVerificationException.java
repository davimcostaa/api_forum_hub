package one.challenge.forum_hub.infra.exceptions;

public class JWTVerificationException extends RuntimeException {
    public JWTVerificationException(String message) {
        super(message);
    }
}
