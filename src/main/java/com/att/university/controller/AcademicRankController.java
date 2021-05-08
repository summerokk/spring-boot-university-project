package com.att.university.controller;

import com.att.university.entity.AcademicRank;
import com.att.university.request.academic_rank.AcademicRankAddRequest;
import com.att.university.request.academic_rank.AcademicRankUpdateRequest;
import com.att.university.service.AcademicRankService;
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
@RequestMapping("/academicranks")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AcademicRankController {
    private final AcademicRankService academicRankService;

    @GetMapping("/")
    public String index(Model model) {
        List<AcademicRank> academicRanks = academicRankService.findAll();

        model.addAttribute("academicRanks", academicRanks);

        return "academic_ranks/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        AcademicRank academicRank = academicRankService.findById(id);

        model.addAttribute("academicRank", academicRank);
        model.addAttribute("updateRequest", new AcademicRankUpdateRequest());

        return "academic_ranks/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute AcademicRankUpdateRequest updateRequest) {
        academicRankService.update(updateRequest);

        return String.format("redirect:/academicranks/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new AcademicRankAddRequest());

        return "academic_ranks/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute AcademicRankAddRequest addRequest, RedirectAttributes redirectAttributes) {
        academicRankService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        academicRankService.deleteById(id);

        return "redirect:/academicranks/";
    }
}
