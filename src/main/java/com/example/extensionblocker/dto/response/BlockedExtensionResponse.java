package com.example.extensionblocker.dto.response;

import java.util.List;

public record BlockedExtensionResponse(
    List<String> blockedExtensions
) {
}
