package epam.com.task_rest.exception;

import epam.com.task_rest.dto.ErrorDto;
import io.swagger.v3.oas.annotations.Hidden;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Hidden
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> handleResourceNotFoundException(ResourceNotFoundException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorDto(404, ex.getMessage(), request.getRequestURI()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorDto> handleAuthenticationException(AuthenticationException ex, HttpServletRequest request) {
        return new ResponseEntity<>(new ErrorDto(401, ex.getMessage(), request.getRequestURI()), HttpStatus.UNAUTHORIZED);
    }
}
