package com.example.extensionblocker.dto.response;

import java.util.List;

public record FixedExtensionListResponse(
        List<FixedExtensionResponse> fixedExtensions
) {
}
