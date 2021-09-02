package com.example.trelloclone.controllers;

import com.example.trelloclone.models.Label;
import com.example.trelloclone.services.LabelService;
import jdk.jshell.spi.ExecutionControl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cards/labels")
public class LabelController {

    private final LabelService labelService;

    @Autowired
    public LabelController(LabelService labelService) {
        this.labelService = labelService;
    }

    @PostMapping(path = "/new")
    public Label createLabel(@RequestBody Label body) {
        return labelService.createLabel(body);
    }

    @PutMapping
    public Label updateLabel(@RequestBody Label body) throws Exception {
        return labelService.updateLabel(body);
    }
}
