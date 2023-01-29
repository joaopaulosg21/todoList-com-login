package aprendendo.api.toDoList.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionsHandler extends ResponseEntityExceptionHandler{

    private final Map<String,String> error = new HashMap<>();

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<Object> runtimeExceptionHandler(RuntimeException exc,WebRequest req) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        error.put("ERROR",exc.getMessage());
        return handleExceptionInternal(exc,error,headers,BAD_REQUEST,req);
    }

    @Override

    public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exc,HttpHeaders headers,
    HttpStatus status,WebRequest request) {
        exc.getBindingResult().getAllErrors().forEach((e) -> {
            String fieldName = ((FieldError)e).getField();
            String message = e.getDefaultMessage();
            error.put(fieldName,message);
        });
        return handleExceptionInternal(exc, error, headers, status, request);
    }

}
