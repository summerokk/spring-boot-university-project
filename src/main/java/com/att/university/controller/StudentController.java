package com.att.university.controller;

import com.att.university.entity.Group;
import com.att.university.entity.Student;
import com.att.university.exception.service.LoginFailException;
import com.att.university.request.person.student.StudentRegisterRequest;
import com.att.university.service.GroupService;
import com.att.university.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.util.List;

@Controller
@RequestMapping("students")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class StudentController {
    private final StudentService studentService;
    private final GroupService groupService;

    @GetMapping("/create")
    public String register(Model model) {
        model.addAttribute("registerRequest", new StudentRegisterRequest());

        return "students/registration";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute StudentRegisterRequest registerRequest, RedirectAttributes attributes) {
        studentService.register(registerRequest);

        attributes.addFlashAttribute("email", registerRequest.getEmail());

        return new ModelAndView("redirect:/students/showLogin");
    }

    @GetMapping("/showLogin")
    public String loginPage() {
        return "students/login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password,
                              RedirectAttributes attributes) {

        if (!studentService.login(email, password)) {
            throw new LoginFailException("Invalid user or password");
        }

        Student student = studentService.findByEmail(email);
        attributes.addAttribute("id", student.getId());

        return new RedirectView("show");
    }

    @GetMapping("/show")
    public String showStudent(@RequestParam Integer id, Model model) {
        Student student = studentService.findById(id);
        List<Group> groups = groupService.findAll();

        model.addAttribute("student", student);
        model.addAttribute("groups", groups);

        return "students/show";
    }

    @GetMapping("/{page}/{count}")
    public String showAllStudents(@PathVariable("page") int page, @PathVariable("count") int count, Model model) {
        List<Student> students = studentService.findAll(page, count);

        int countPages = (int) Math.ceil((double) studentService.count() / count);

        model.addAttribute("students", students);
        model.addAttribute("countPages", countPages);
        model.addAttribute("page", page);
        model.addAttribute("count", count);

        return "students/all";
    }
}
