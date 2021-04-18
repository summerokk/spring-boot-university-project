package com.att.university.provider.lesson;

import com.att.university.entity.Lesson;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LessonPdfFileProvider {
    void provideFile(HttpServletResponse response, List<Lesson> lessons) throws Exception;
}
