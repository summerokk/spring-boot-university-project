package com.att.university.controller;

import com.att.university.entity.Lesson;
import com.att.university.entity.Teacher;
import com.att.university.exception.dao.PersonNotFoundException;
import com.att.university.exception.service.lesson.LessonSearchException;
import com.att.university.provider.lesson.LessonPdfFileProvider;
import com.att.university.request.lesson.LessonAddRequest;
import com.att.university.request.lesson.LessonUpdateRequest;
import com.att.university.service.ClassroomService;
import com.att.university.service.CourseService;
import com.att.university.service.GroupService;
import com.att.university.service.LessonService;
import com.att.university.service.TeacherService;
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
import java.time.LocalDate;
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
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(1);

        List<Lesson> lessons = lessonService.findByDateBetween(startDate, endDate);
        List<Teacher> teachers = teacherService.findAllWithoutPagination();
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

        try {
            Teacher teacher = teacherService.findById(teacherId);

            List<LocalDate> lessonWeeks = lessonService.findTeacherLessonWeeks(startDate, endDate, teacherId);

            List<Lesson> lessons = lessonService.findTeacherWeekSchedule(currentPage, lessonWeeks, teacherId);

            List<Teacher> teachers = teacherService.findAllWithoutPagination();

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
    public void exportSchedulePdf(@RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate startDate,
                                  @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                          LocalDate endDate, @RequestParam Integer teacherId,
                                  HttpServletResponse response) throws Exception {
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
        model.addAttribute("teachers", teacherService.findAllWithoutPagination());
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
        model.addAttribute("teachers", teacherService.findAllWithoutPagination());
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
