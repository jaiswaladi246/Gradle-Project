package com.devopsshack.gradleboard.controller;

import com.devopsshack.gradleboard.model.BuildRecord;
import com.devopsshack.gradleboard.service.BuildService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class DashboardController {

    private final BuildService buildService;

    public DashboardController(BuildService buildService) {
        this.buildService = buildService;
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        model.addAttribute("builds", buildService.findAll());
        model.addAttribute("summary", buildService.summary());
        return "index";
    }

    @PostMapping("/builds")
    public String triggerBuild(
            @RequestParam String serviceName,
            @RequestParam(defaultValue = "main") String branch,
            @RequestParam(defaultValue = "DEV") String environment,
            RedirectAttributes redirectAttributes) {

        BuildRecord build = buildService.triggerBuild(serviceName, branch, environment);
        redirectAttributes.addFlashAttribute(
                "message",
                "Build #" + build.id() + " finished with status " + build.status());
        redirectAttributes.addFlashAttribute("messageStatus", build.status().name().toLowerCase());
        return "redirect:/";
    }
}
