package com.att.university.controller;

import com.att.university.entity.Classroom;
import com.att.university.request.classroom.ClassroomAddRequest;
import com.att.university.request.classroom.ClassroomUpdateRequest;
import com.att.university.service.BuildingService;
import com.att.university.service.ClassroomService;
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
@RequestMapping("/classrooms")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ClassroomController {
    private final ClassroomService classroomService;
    private final BuildingService buildingService;

    @GetMapping("/")
    public String index(Model model) {
        List<Classroom> classrooms = classroomService.findAll();

        model.addAttribute("classrooms", classrooms);

        return "classrooms/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Classroom classroom = classroomService.findById(id);

        model.addAttribute("classroom", classroom);
        model.addAttribute("updateRequest", new ClassroomUpdateRequest());
        model.addAttribute("buildings", buildingService.findAll());

        return "classrooms/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ClassroomUpdateRequest updateRequest) {
        classroomService.update(updateRequest);

        return String.format("redirect:/classrooms/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new ClassroomAddRequest());
        model.addAttribute("buildings", buildingService.findAll());

        return "classrooms/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute ClassroomAddRequest addRequest, RedirectAttributes redirectAttributes) {
        classroomService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        classroomService.deleteById(id);

        return "redirect:/classrooms/";
    }
}
