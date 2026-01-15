package com.example.extensionblocker.controller.ui;

import com.example.extensionblocker.dto.request.BlockFixedExtensionRequest;
import com.example.extensionblocker.dto.ui.BlockFixedExtensionRequestForm;
import com.example.extensionblocker.dto.ui.CreateCustomExtensionForm;
import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.ExtensionListResponse;
import com.example.extensionblocker.exception.ExtensionException;
import com.example.extensionblocker.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ExtensionController {

    private final ExtensionService extensionService;

    @GetMapping("/")
    public String home(Model model) {

        ExtensionListResponse allExtensions = extensionService.getAllExtensions();
        model.addAttribute("data", allExtensions);
        model.addAttribute("form", new CreateCustomExtensionForm());

        return "extensions";
    }

    @PostMapping("/ui/fixed-extensions")
    public String updateFixedExtensions(@ModelAttribute BlockFixedExtensionRequestForm form) {

        List<Integer> blocked = form.getBlockedFixedExtensionList();
        if (blocked == null) blocked = List.of();   // 전부 uncheck 대응

        extensionService.changeFixedExtensionStatus(
                new BlockFixedExtensionRequest(blocked)
        );
        return "redirect:/";
    }

    @PostMapping("/ui/custom-extensions")
    public String customCustomExtension(
            @Valid @ModelAttribute("form") CreateCustomExtensionForm form,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("data", extensionService.getAllExtensions());
            return "extensions";
        }

        try {
            extensionService.createCustomExtension(
                    new CreateCustomExtensionRequest(form.getExtensionName())
            );

        } catch (ExtensionException e) {
            bindingResult.rejectValue("extensionName", "extension.error", e.getMessage());
            model.addAttribute("data", extensionService.getAllExtensions());
            return "extensions";
        }

        return "redirect:/";
    }

    @PostMapping("/ui/custom-extensions/{id}/delete")
    public String deleteCustomExtensions(@PathVariable Integer id, Model model) {

        extensionService.deleteCustomExtension(id);
        return "redirect:/";
    }
}
