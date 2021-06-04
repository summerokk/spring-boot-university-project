package com.att.provider.lesson;

import com.att.entity.Lesson;

import java.io.OutputStream;
import java.util.List;

public interface LessonPdfFileProvider {
    void provideFile(OutputStream outputStream, List<Lesson> lessons);
}
