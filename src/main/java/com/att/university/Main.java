package com.att.university;

import com.att.university.dao.LessonDao;
import com.att.university.dao.impl.LessonDaoImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);

        LessonDao lessonDao = context.getBean("lessonDao", LessonDaoImpl.class);
        System.out.println(lessonDao.findById(1));
    }
}
