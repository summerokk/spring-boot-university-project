package com.att.university.controller;

import com.att.university.entity.Building;
import com.att.university.request.building.BuildingAddRequest;
import com.att.university.request.building.BuildingUpdateRequest;
import com.att.university.service.BuildingService;
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
@RequestMapping("/buildings")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BuildingController {
    private final BuildingService buildingService;

    @GetMapping("/")
    public String index(Model model) {
        List<Building> buildings = buildingService.findAll();

        model.addAttribute("buildings", buildings);

        return "buildings/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Building building = buildingService.findById(id);

        model.addAttribute("building", building);
        model.addAttribute("updateRequest", new BuildingUpdateRequest());

        return "buildings/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BuildingUpdateRequest updateRequest) {
        buildingService.update(updateRequest);

        return String.format("redirect:/buildings/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new BuildingAddRequest());

        return "buildings/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute BuildingAddRequest addRequest, RedirectAttributes redirectAttributes) {
        buildingService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        buildingService.deleteById(id);

        return "redirect:/buildings/";
    }
}
