package com.att.university.exception;

import com.att.university.controller.TeacherController;
import com.att.university.exception.dao.AcademicRankNotFoundException;
import com.att.university.exception.dao.ScienceDegreeNotFoundException;
import com.att.university.exception.service.LoginFailException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = TeacherController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class TeacherControllerAdvice {
    @ExceptionHandler(LoginFailException.class)
    public String loginFail(Exception exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("loginFail", exception.getMessage());

        return "redirect:/teachers/showLogin";
    }

    @ExceptionHandler({ScienceDegreeNotFoundException.class, AcademicRankNotFoundException.class})
    public String scienceDegreeNotFound(Exception exception, RedirectAttributes attributes, WebRequest request) {
        attributes.addFlashAttribute("error", exception.getMessage());

        return "redirect:" + request.getHeader("Referer");
    }
}
