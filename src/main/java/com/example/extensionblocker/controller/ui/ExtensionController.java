package com.example.extensionblocker.controller.ui;

import com.example.extensionblocker.service.ExtensionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    @GetMapping("/")
    public String home() {
        return "extensions";
    }
}
