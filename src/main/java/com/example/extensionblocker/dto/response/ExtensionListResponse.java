package com.example.extensionblocker.dto.response;

import java.util.List;

public record ExtensionListResponse(
        List<FixedExtensionResponse> fixedExtensionList,
        List<CustomExtensionResponse> customExtensionList,
        Integer customExtensionCount,
        Integer customExtensionLimit
) {
}
