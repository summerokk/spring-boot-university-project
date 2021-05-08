package com.att.university.controller;

import com.att.university.entity.Course;
import com.att.university.request.course.CourseAddRequest;
import com.att.university.request.course.CourseUpdateRequest;
import com.att.university.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.List;

@Controller
@RequestMapping("/courses")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/")
    public String index(Model model) {
        List<Course> courses = courseService.findAll();

        model.addAttribute("courses", courses);

        return "courses/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Course course = courseService.findById(id);

        model.addAttribute("course", course);
        model.addAttribute("updateRequest", new CourseUpdateRequest());

        return "courses/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute CourseUpdateRequest updateRequest) {
        courseService.update(updateRequest);

        return String.format("redirect:/courses/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new CourseAddRequest());

        return "courses/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute CourseAddRequest addRequest, RedirectAttributes redirectAttributes) {
        courseService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        courseService.deleteById(id);

        return "redirect:/courses/";
    }
}
