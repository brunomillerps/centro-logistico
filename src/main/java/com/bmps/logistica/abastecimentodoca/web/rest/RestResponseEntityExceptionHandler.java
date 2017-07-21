package com.bmps.logistica.abastecimentodoca.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handle(Exception ex, HttpServletRequest req) {

        log.error("Erro na API REST " + req.getRequestURI() + " - parametros - " + Arrays.toString(req.getParameterMap().entrySet().toArray()), ex);

        StringBuilder erro = new StringBuilder();
        erro.append("Não foi possível processar a requisição.");
        erro.append("Motivo --------------- >");
        erro.append(ex.getMessage());

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .header("Content-type", MediaType.TEXT_PLAIN_VALUE + ";charset=UTF-8")
                .body(erro.toString());
    }
}
