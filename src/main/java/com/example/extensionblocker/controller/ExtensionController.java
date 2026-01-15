package com.example.extensionblocker.controller;

import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.CustomExtensionResponse;
import com.example.extensionblocker.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/extensions")
public class ExtensionController {

    private final ExtensionService extensionService;

    @PostMapping("/customs")
    public ResponseEntity<CustomExtensionResponse> createCustomExtension(@Valid @RequestBody CreateCustomExtensionRequest request) {
        CustomExtensionResponse customExtension = extensionService.createCustomExtension(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customExtension);
    }
}
