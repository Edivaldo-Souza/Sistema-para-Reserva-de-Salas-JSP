package com.dunnas.reservasalas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {

        logger.error("Ocorreu uma exceção não esperada: ", ex);

        model.addAttribute("mensagemErro",
                ex.getMessage());

        return "error";
    }
}