package com.att.university.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView exceptionHandle(Exception exception, WebRequest request) {
        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.addObject("error", exception.getMessage());

        return modelAndView;
    }

    @ExceptionHandler(value = NullPointerException.class)
    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    public String runtimeExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
