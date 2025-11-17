package com.openclassrooms.front.config;

import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(FeignException.Unauthorized.class)
    public String handleUnauthorized(FeignException.Unauthorized exception, RedirectAttributes model) {
        model.addFlashAttribute("error", "Unauthorized");
        log.error(exception.getMessage());
        return "redirect:/login";
    }

    @ExceptionHandler(org.springframework.web.servlet.NoHandlerFoundException.class)
    public String handleNotFound(Exception exception, RedirectAttributes model) {
        model.addFlashAttribute("error", "Page introuvable");
        log.error(exception.getMessage());
        return "redirect:/patients";
    }
}
