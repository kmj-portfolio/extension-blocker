package com.example.extensionblocker.dto.request;

import java.util.List;

public record BlockFixedExtensionRequest(
        List<Integer> blockedFixedExtensionList
) {
}
