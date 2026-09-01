package com.devopsshack.gradleboard.controller;

import com.devopsshack.gradleboard.model.BuildRecord;
import com.devopsshack.gradleboard.model.BuildSummary;
import com.devopsshack.gradleboard.service.BuildService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ApiController {

    private final BuildService buildService;

    public ApiController(BuildService buildService) {
        this.buildService = buildService;
    }

    @GetMapping("/builds")
    public List<BuildRecord> builds() {
        return buildService.findAll();
    }

    @GetMapping("/summary")
    public BuildSummary summary() {
        return buildService.summary();
    }
}
