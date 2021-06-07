package com.att.controller;

import com.att.request.group.GroupAddRequest;
import com.att.request.group.GroupUpdateRequest;
import com.att.entity.Group;
import com.att.service.FacultyService;
import com.att.service.GroupService;
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
@RequestMapping("/groups")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class GroupController {
    private final GroupService groupService;
    private final FacultyService facultyService;

    @GetMapping("/")
    public String index(Model model) {
        List<Group> groups = groupService.findAll();

        model.addAttribute("groups", groups);

        return "groups/all";
    }

    @GetMapping("/show/{id}")
    public String show(@PathVariable Integer id, Model model) {
        Group group = groupService.findById(id);

        model.addAttribute("group", group);
        model.addAttribute("updateRequest", new GroupUpdateRequest());
        model.addAttribute("faculties", facultyService.findAll());

        return "groups/show";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute GroupUpdateRequest updateRequest) {
        groupService.update(updateRequest);

        return String.format("redirect:/groups/show/%d", updateRequest.getId());
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("addRequest", new GroupAddRequest());
        model.addAttribute("faculties", facultyService.findAll());

        return "groups/add";
    }

    @PostMapping("/store")
    public RedirectView store(@ModelAttribute GroupAddRequest addRequest, RedirectAttributes redirectAttributes) {
        groupService.create(addRequest);

        redirectAttributes.addFlashAttribute("successCreate", "success");

        return new RedirectView("create");
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam Integer id) {
        groupService.deleteById(id);

        return "redirect:/groups/";
    }
}
