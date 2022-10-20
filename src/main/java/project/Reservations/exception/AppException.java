package project.Reservations.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@Getter @Setter
public class AppException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    private HttpStatus estado;
    private String mensaje;

    public AppException(HttpStatus estado, String mensaje) {
        super();
        this.estado = estado;
        this.mensaje = mensaje;
    }

}

