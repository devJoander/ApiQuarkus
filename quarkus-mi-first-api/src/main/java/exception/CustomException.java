package exception;

import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class CustomException extends WebApplicationException {
    public CustomException(Response.Status status, String message, String cause) {
        super(Response.status(status)
                .entity(new Mensaje(message, cause))
                .type(MediaType.APPLICATION_JSON)
                .build());
    }
}
