package com.att.university.controller;

import com.att.university.dao.LessonDao;
import com.att.university.entity.Lesson;
import com.att.university.entity.Teacher;
import com.att.university.provider.lesson.LessonPdfFileProvider;
import com.att.university.service.LessonService;
import com.att.university.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonController {
    private final LessonService lessonService;
    private final LessonDao lessonDao;
    private final TeacherService teacherService;
    private final LessonPdfFileProvider pdfFleProvider;

    @GetMapping("/")
    public String index(Model model) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);

        List<Lesson> lessons = lessonDao.findByDateBetween(startDate, endDate);
        List<Teacher> teachers = teacherService.findAll();
        Teacher teacher = teachers.get(0);

        model.addAttribute("lessons", lessons);
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentTeacher", teacher);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "lessons/all";
    }

    @GetMapping("/find")
    public String findLessons(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                      LocalDate endDate, @RequestParam Integer teacherId,
                              @RequestParam(defaultValue = "1", name = "page") int currentPage, Model model) {
        Teacher teacher = teacherService.findById(teacherId);

        List<LocalDate> lessonWeeks = lessonService.findTeacherLessonWeeks(startDate, endDate, teacherId);

        List<Lesson> lessons = lessonService.findTeacherWeekSchedule(currentPage, lessonWeeks, teacherId);

        List<Teacher> teachers = teacherService.findAll();

        model.addAttribute("lessons", lessons);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("teachers", teachers);
        model.addAttribute("currentTeacher", teacher);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("lessonWeeks", lessonWeeks);

        return "lessons/all";
    }

    @GetMapping("/pdf")
    public void exportSchedulePdf(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate startDate,
                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                              LocalDate endDate, @RequestParam Integer teacherId,
                                  HttpServletResponse response) throws Exception {
        List<Lesson> lessons = lessonService.findByDateBetweenAndTeacherId(startDate, endDate, teacherId);

        pdfFleProvider.provideFile(response, lessons);
    }
}
