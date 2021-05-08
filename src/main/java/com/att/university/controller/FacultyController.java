package com.att.university.controller;

import com.att.university.entity.Faculty;
import com.att.university.request.faculty.FacultyAddRequest;
import com.att.university.request.faculty.FacultyUpdateRequest;
import com.att.university.service.FacultyService;
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
@RequestMapping("/faculties")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FacultyController {
    private final FacultyService facultyService;

    @GetMapping("/")
    public String index(Model model) {
        List<Faculty> faculties = facultyService.findAll();

        model.addAttribute("faculties", faculties);

        return "faculties/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Faculty faculty = facultyService.findById(id);

        model.addAttribute("faculty", faculty);
        model.addAttribute("updateRequest", new FacultyUpdateRequest());

        return "faculties/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute FacultyUpdateRequest updateRequest) {
        facultyService.update(updateRequest);

        return String.format("redirect:/faculties/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new FacultyAddRequest());

        return "faculties/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute FacultyAddRequest addRequest, RedirectAttributes redirectAttributes) {
        facultyService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        facultyService.deleteById(id);

        return "redirect:/faculties/";
    }
}
