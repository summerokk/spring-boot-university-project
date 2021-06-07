package com.att.advice;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ExceptionHandlerAdvice {
    @ExceptionHandler(value = Exception.class)
    public ModelAndView exceptionHandle(Exception exception, HttpServletRequest request) {

        ModelAndView modelAndView = new ModelAndView("errors/error");
        modelAndView.addObject("requestUrl", request.getRequestURL());
        modelAndView.addObject("exception", exception);

        return modelAndView;
    }

    @ExceptionHandler(value = NullPointerException.class)
    public String nullPointerExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("error", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
