package com.example.extensionblocker.mapper;

import com.example.extensionblocker.dto.response.CustomExtensionResponse;
import com.example.extensionblocker.entity.CustomExtensionEntity;
import org.springframework.stereotype.Component;

@Component
public class ExtensionMapper {

    public CustomExtensionResponse toCustomExtensionResponse(CustomExtensionEntity customExtension) {
        return new CustomExtensionResponse(customExtension.getId(),
                        customExtension.getExtensionName()
        );
    }
}
