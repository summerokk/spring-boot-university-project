package com.att.university;

import com.att.university.controller.FrontController;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConsoleUniversityApplication {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        FrontController frontController = context.getBean("frontController", FrontController.class);
        frontController.runApplication();
    }
}
