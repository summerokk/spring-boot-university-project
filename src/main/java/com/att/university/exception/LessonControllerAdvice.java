package com.att.university.exception;

import com.att.university.controller.LessonController;
import com.att.university.exception.service.lesson.LessonSearchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice(assignableTypes = LessonController.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class LessonControllerAdvice {

    @ExceptionHandler(value = LessonSearchException.class)
    public String personNotFoundException(Exception exception, RedirectAttributes attributes) {
        attributes.addFlashAttribute("lessonSearchError", exception.getMessage());

        return "redirect:/lessons/";
    }
}
