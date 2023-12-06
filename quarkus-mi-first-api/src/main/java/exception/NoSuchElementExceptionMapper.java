package exception;

import java.util.NoSuchElementException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NoSuchElementExceptionMapper implements ExceptionMapper<NoSuchElementException> {

    public class NoSuchElementMessage {
        private final String message;
        private final String detail;
    
        public NoSuchElementMessage(String message, String detail) {
            this.message = message;
            this.detail = detail;
        }

        public String getMessage() {
            return message;
        }

        public String getDetail() {
            return detail;
        }
    }
    
    @Override
    public Response toResponse(NoSuchElementException exception) {
        var error = new NoSuchElementMessage(exception.getMessage(), null);
        return Response.status(404).entity(error).build();
     }
    
}
