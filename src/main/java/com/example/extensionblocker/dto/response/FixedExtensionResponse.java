package com.example.extensionblocker.dto.response;

public record FixedExtensionResponse(
        Integer id,
        String extensionName,
        boolean isBlocked
) {
}
