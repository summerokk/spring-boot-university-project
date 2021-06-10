package com.att.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(Exception.class)
    public ModelAndView exceptionHandle(Exception exception, HttpServletRequest request) {
        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.addObject("requestUrl", request.getRequestURL());
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(NullPointerException.class)
    public String nullPointerExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public String handleValidationExceptions(ConstraintViolationException exception, WebRequest request,
                                             RedirectAttributes attributes) {
        List<String> errors = exception.getConstraintViolations()
                .stream()
                .map(ConstraintViolation::getMessage)
                .collect(Collectors.toList());

        attributes.addFlashAttribute("validationErrors", errors);

        return "redirect:" + request.getHeader("Referer");
    }
}
