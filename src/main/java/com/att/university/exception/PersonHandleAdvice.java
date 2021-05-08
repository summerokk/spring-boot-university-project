package com.att.university.exception;

import com.att.university.exception.service.EmailAlreadyExistsException;
import com.att.university.exception.service.NameIncorrectException;
import com.att.university.exception.service.PasswordTooShortException;
import com.att.university.exception.service.PasswordsAreNotTheSameException;
import com.att.university.exception.service.WrongEmailFormatException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PersonHandleAdvice {
    @ExceptionHandler(value = NameIncorrectException.class)
    public String incorrectNameExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("incorrectName", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(value = EmailAlreadyExistsException.class)
    public String emailExistsExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("emailExistsFail", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(value = PasswordTooShortException.class)
    public String shortPasswordExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("passwordTooShort", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(value = PasswordsAreNotTheSameException.class)
    public String samePasswordsExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("passwordNotTheSame", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }

    @ExceptionHandler(value = WrongEmailFormatException.class)
    public String emailFormatExceptionHandle(Exception exception, WebRequest request, RedirectAttributes attributes) {
        attributes.addFlashAttribute("wrongEmailFormat", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
