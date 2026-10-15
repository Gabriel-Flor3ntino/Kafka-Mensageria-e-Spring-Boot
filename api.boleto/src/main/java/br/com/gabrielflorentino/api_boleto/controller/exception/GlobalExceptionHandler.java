package br.com.gabrielflorentino.api_boleto.controller.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> methodArgumentNotValidException(MethodArgumentNotValidException e, WebRequest request) {
        var erros = e.getFieldErrors()
                .stream()
                .map(item -> item.getField() + " " + item.getDefaultMessage() + " ")
                .collect(Collectors.joining());

        var response = ErroResponse.builder()
                .erro(erros)
                .codigo(HttpStatus.BAD_REQUEST.value())
                .timestamp(new Date())
                .path(request.getDescription(false)).build();

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
