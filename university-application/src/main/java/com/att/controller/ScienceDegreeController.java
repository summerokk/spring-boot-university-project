package com.att.controller;

import com.att.request.science_degree.ScienceDegreeAddRequest;
import com.att.request.science_degree.ScienceDegreeUpdateRequest;
import com.att.entity.ScienceDegree;
import com.att.service.ScienceDegreeService;
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
@RequestMapping("/sciencedegrees")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ScienceDegreeController {
    private final ScienceDegreeService scienceDegreeService;

    @GetMapping("/")
    public String index(Model model) {
        List<ScienceDegree> scienceDegrees = scienceDegreeService.findAll();

        model.addAttribute("scienceDegrees", scienceDegrees);

        return "science_degrees/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        ScienceDegree scienceDegree = scienceDegreeService.findById(id);

        model.addAttribute("scienceDegree", scienceDegree);
        model.addAttribute("updateRequest", new ScienceDegreeUpdateRequest());

        return "science_degrees/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute ScienceDegreeUpdateRequest updateRequest) {
        scienceDegreeService.update(updateRequest);

        return String.format("redirect:/sciencedegrees/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new ScienceDegreeAddRequest());

        return "science_degrees/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute ScienceDegreeAddRequest addRequest, RedirectAttributes redirectAttributes) {
        scienceDegreeService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        scienceDegreeService.deleteById(id);

        return "redirect:/sciencedegrees/";
    }
}
