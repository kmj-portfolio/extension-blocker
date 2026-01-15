package com.example.extensionblocker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateCustomExtensionRequest(
        @NotBlank
        @Size(min = 1, max = 20)
        String extensionName
) {
}
