package com.example.extensionblocker.controller.api;

import com.example.extensionblocker.dto.request.BlockFixedExtensionRequest;
import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.*;
import com.example.extensionblocker.service.ExtensionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "확장자 관리 API", description = "파일 확장자 차단 관리 및 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/extensions")
public class ExtensionRestController {

    private final ExtensionService extensionService;

    @Operation(summary = "모든 확장자 조회", description = "고정 확장자와 커스텀 확장자를 각각의 목록으로 조회합니다.")
    @GetMapping
    public ResponseEntity<ExtensionListResponse> getAllExtensions() {
        ExtensionListResponse response = extensionService.getAllExtensions();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "차단한 확장자 목록 조회", description = "차단한 고정 확장자와 커스텀 확장자를 조회합니다.")
    @GetMapping("/blocked")
    public ResponseEntity<BlockedExtensionResponse> getBlockedExtensions() {
        BlockedExtensionResponse response = extensionService.getBlockedExtensions();
        return ResponseEntity.ok(response);
    }

    // 고정 확장자의 차단 여부를 변경
    @Operation(summary = "고정 확장자 차단 목록 수정", description = "차단한 고정 확장자 목록을 수정합니다.")
    @PutMapping("/fixeds")
    public ResponseEntity<FixedExtensionListResponse> updateBlockedFixedExtensionStatus(@RequestBody BlockFixedExtensionRequest request) {
        FixedExtensionListResponse response = extensionService.changeFixedExtensionStatus(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "커스텀 확장자 목록 조회", description = "커스텀 확장자 목록을 조회합니다.")
    @GetMapping("/customs")
    public ResponseEntity<CustomExtensionListResponse> getCustomExtensions() {
        CustomExtensionListResponse response = extensionService.getCustomExtensionList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "커스텀 확장자 생성", description = "커스텀 확장자를 생성합니다.")
    @PostMapping("/customs")
    public ResponseEntity<CustomExtensionResponse> createCustomExtension(@Valid @RequestBody CreateCustomExtensionRequest request) {
        CustomExtensionResponse customExtension = extensionService.createCustomExtension(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(customExtension);
    }

    @Operation(summary = "커스텀 확장자 삭제", description = "커스텀 확장자를 삭제합니다.")
    @DeleteMapping("/customs/{id}")
    public ResponseEntity<Void> deleteCustomExtension(@PathVariable Integer id) {
        extensionService.deleteCustomExtension(id);

        return ResponseEntity
                .noContent()
                .build();
    }
}
