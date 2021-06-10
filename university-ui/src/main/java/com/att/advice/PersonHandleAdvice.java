package com.att.advice;

import com.att.exception.service.EmailAlreadyExistsException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PersonHandleAdvice {
    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public String emailExistsExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("emailExistsFail", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
