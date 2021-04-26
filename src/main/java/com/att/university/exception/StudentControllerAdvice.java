package com.att.university.exception;

import com.att.university.controller.StudentController;
import com.att.university.exception.service.LoginFailException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = StudentController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StudentControllerAdvice {
    @ExceptionHandler(LoginFailException.class)
    public String loginFail(Exception exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("loginFail", exception.getMessage());

        return "redirect:/students/showLogin";
    }
}
