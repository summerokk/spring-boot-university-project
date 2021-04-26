package com.att.university.provider.lesson;

import com.att.university.entity.Lesson;

import java.io.OutputStream;
import java.util.List;

public interface LessonPdfFileProvider {
    void provideFile(OutputStream outputStream, List<Lesson> lessons);
}
