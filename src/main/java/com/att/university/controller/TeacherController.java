package com.att.university.controller;

import com.att.university.entity.AcademicRank;
import com.att.university.entity.ScienceDegree;
import com.att.university.entity.Teacher;
import com.att.university.exception.service.LoginFailException;
import com.att.university.request.person.teacher.TeacherRegisterRequest;
import com.att.university.request.person.teacher.TeacherUpdateRequest;
import com.att.university.service.AcademicRankService;
import com.att.university.service.ScienceDegreeService;
import com.att.university.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("teachers")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TeacherController {

    @Value("${person.default.page}")
    private int defaultPage;

    @Value("${person.default.items}")
    private int itemsOnPage;

    private final TeacherService teacherService;
    private final ScienceDegreeService scienceDegreeService;
    private final AcademicRankService academicRankService;

    @GetMapping("/create")
    public String register(Model model) {
        model.addAttribute("registerRequest", new TeacherRegisterRequest());
        model.addAttribute("academicRanks", scienceDegreeService.findAll());
        model.addAttribute("scienceDegrees", academicRankService.findAll());

        return "teachers/registration";
    }

    @PostMapping("/register")
    public ModelAndView register(@ModelAttribute TeacherRegisterRequest registerRequest, RedirectAttributes attributes) {
        teacherService.register(registerRequest);

        attributes.addFlashAttribute("email", registerRequest.getEmail());

        return new ModelAndView("redirect:/teachers/showLogin");
    }

    @GetMapping("/showLogin")
    public String loginPage() {
        return "teachers/login";
    }

    @PostMapping("/login")
    public RedirectView login(@RequestParam String email, @RequestParam String password,
                              RedirectAttributes attributes) {

        if (!teacherService.login(email, password)) {
            throw new LoginFailException("Invalid user or password");
        }

        Teacher teacher = teacherService.findByEmail(email);
        attributes.addAttribute("id", teacher.getId());

        return new RedirectView("show");
    }

    @GetMapping("/show")
    public String showTeacher(@RequestParam Integer id, Model model) {
        Teacher teacher = teacherService.findById(id);
        List<ScienceDegree> scienceDegrees = scienceDegreeService.findAll();
        List<AcademicRank> academicRanks = academicRankService.findAll();

        model.addAttribute("teacher", teacher);
        model.addAttribute("academicRanks", scienceDegrees);
        model.addAttribute("scienceDegrees", academicRanks);
        model.addAttribute("updateRequest", new TeacherUpdateRequest());

        return "teachers/show";
    }

    @GetMapping("/{page}/{count}")
    public String showAllTeacher(@PathVariable("page") int page, @PathVariable("count") int count, Model model) {
        Page<Teacher> teachers = teacherService.findAll(PageRequest.of(page - 1, count));

        model.addAttribute("teachers", teachers);

        return "teachers/all";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute TeacherUpdateRequest updateRequest) {
        teacherService.update(updateRequest);

        return String.format("redirect:/teachers/show?id=%d", updateRequest.getId());
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        teacherService.deleteById(id);

        return String.format("redirect:/teachers/%d/%d", defaultPage, itemsOnPage);
    }
}
