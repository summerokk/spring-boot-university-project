package com.att.university.exception;

import com.att.university.controller.StudentController;
import com.att.university.exception.service.LoginFailException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = StudentController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StudentControllerAdvice {

    @ExceptionHandler(value = RuntimeException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String loginFailExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("loginFail", exception.getMessage());
        exception.getLocalizedMessage();
        return "redirect:" + request.getHeader("Referer");
    }
}
