package com.example.extensionblocker.controller;

import com.example.extensionblocker.dto.request.BlockFixedExtensionRequest;
import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.*;
import com.example.extensionblocker.service.ExtensionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/extensions")
public class ExtensionController {

    private final ExtensionService extensionService;

    @GetMapping
    public ResponseEntity<ExtensionListResponse> getAllExtensions() {
        ExtensionListResponse response = extensionService.getAllExtensions();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/blocked")
    public ResponseEntity<BlockedExtensionResponse> getBlockedExtensions() {
        BlockedExtensionResponse response = extensionService.getBlockedExtensions();
        return ResponseEntity.ok(response);
    }

    // 고정 확장자의 차단 여부를 변경
    @PutMapping("/fixed")
    public ResponseEntity<FixedExtensionListResponse> updateBlockedFixedExtensionStatus(@RequestBody BlockFixedExtensionRequest request) {
        FixedExtensionListResponse response = extensionService.changeFixedExtensionStatus(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/customs")
    public ResponseEntity<CustomExtensionListResponse> getCustomExtensions() {
        CustomExtensionListResponse response = extensionService.getCustomExtensionList();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/customs")
    public ResponseEntity<CustomExtensionResponse> createCustomExtension(@Valid @RequestBody CreateCustomExtensionRequest request) {
        CustomExtensionResponse customExtension = extensionService.createCustomExtension(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customExtension);
    }

    @DeleteMapping("/customs/{id}")
    public ResponseEntity<Void> deleteCustomExtension(@PathVariable Integer id) {
        extensionService.deleteCustomExtension(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
