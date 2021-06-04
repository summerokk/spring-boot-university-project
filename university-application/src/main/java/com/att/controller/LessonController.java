package com.att.controller;

import com.att.exception.dao.PersonNotFoundException;
import com.att.exception.service.lesson.LessonSearchException;
import com.att.provider.lesson.LessonPdfFileProvider;
import com.att.request.lesson.LessonAddRequest;
import com.att.request.lesson.LessonUpdateRequest;
import com.att.entity.Lesson;
import com.att.entity.Teacher;
import com.att.service.ClassroomService;
import com.att.service.CourseService;
import com.att.service.GroupService;
import com.att.service.LessonService;
import com.att.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/lessons")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class LessonController {
    private final LessonService lessonService;
    private final TeacherService teacherService;
    private final GroupService groupService;
    private final CourseService courseService;
    private final ClassroomService classroomService;
    private final LessonPdfFileProvider fileProvider;

    @GetMapping("/")
    public String index(Model model) {
        LocalDateTime endDate = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        LocalDateTime startDate = endDate.minusMonths(1).truncatedTo(ChronoUnit.MINUTES);

        List<Lesson> lessons = lessonService.findByDateBetween(startDate, endDate);
        List<Teacher> teachers = teacherService.findAll();

        model.addAttribute("lessons", lessons);
        model.addAttribute("teachers", teachers);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);

        return "lessons/all";
    }

    @GetMapping("/find")
    public String findLessons(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      LocalDateTime startDate,
                              @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                      LocalDateTime endDate, @RequestParam Integer teacherId,
                              @RequestParam(defaultValue = "1", name = "page") int currentPage, Model model) {
        try {
            Teacher teacher = teacherService.findById(teacherId);

            List<LocalDateTime> lessonWeeks = lessonService.findTeacherLessonWeeks(startDate, endDate, teacherId);

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
        } catch (PersonNotFoundException e) {
            throw new LessonSearchException(e.getMessage());
        }
    }

    @GetMapping("/pdf")
    public void exportSchedulePdf(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          LocalDateTime startDate,
                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                          LocalDateTime endDate, @RequestParam Integer teacherId,
                                  HttpServletResponse response) throws IOException {
        List<Lesson> lessons = lessonService.findByDateBetweenAndTeacherId(startDate, endDate, teacherId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=lessons.pdf");

        fileProvider.provideFile(response.getOutputStream(), lessons);
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Lesson lesson = lessonService.findById(id);

        model.addAttribute("lesson", lesson);
        model.addAttribute("updateRequest", new LessonUpdateRequest());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("courses", courseService.findAll());

        return "lessons/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute LessonUpdateRequest updateRequest) {
        lessonService.update(updateRequest);

        return String.format("redirect:/lessons/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new LessonAddRequest());
        model.addAttribute("classrooms", classroomService.findAll());
        model.addAttribute("teachers", teacherService.findAll());
        model.addAttribute("groups", groupService.findAll());
        model.addAttribute("courses", courseService.findAll());

        return "lessons/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute LessonAddRequest addRequest, RedirectAttributes redirectAttributes) {
        lessonService.add(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        lessonService.deleteById(id);

        return "redirect:/lessons/";
    }
}
