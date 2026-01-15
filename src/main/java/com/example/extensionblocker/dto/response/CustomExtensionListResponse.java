package com.example.extensionblocker.dto.response;

import java.util.List;

public record CustomExtensionListResponse(
        List<CustomExtensionResponse> customExtensionList
) {
}
