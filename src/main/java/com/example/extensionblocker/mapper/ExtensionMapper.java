package com.example.extensionblocker.mapper;

import com.example.extensionblocker.dto.response.CustomExtensionResponse;
import com.example.extensionblocker.dto.response.ExtensionListResponse;
import com.example.extensionblocker.dto.response.FixedExtensionResponse;
import com.example.extensionblocker.entity.CustomExtensionEntity;
import com.example.extensionblocker.entity.FixedExtensionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ExtensionMapper {

    private static final Integer CUSTOM_EXTENSION_LIMIT = 200;

    public CustomExtensionResponse toCustomExtensionResponse(CustomExtensionEntity customExtension) {
        return new CustomExtensionResponse(customExtension.getId(),
                        customExtension.getExtensionName()
        );
    }

    public FixedExtensionResponse toFixedExtensionResponse(FixedExtensionEntity fixedExtension) {
        return new FixedExtensionResponse(
                fixedExtension.getId(),
                fixedExtension.getExtensionName(),
                fixedExtension.isBlocked()
        );
    }

    public ExtensionListResponse toExtensionListResponse(List<FixedExtensionEntity> fixedExtensionEntityList,
                                                         List<CustomExtensionEntity> customExtensionEntityList) {

        List<FixedExtensionResponse> fixedExtensionResponseList = fixedExtensionEntityList.stream()
                .map(this::toFixedExtensionResponse)
                .collect(Collectors.toList());

        List<CustomExtensionResponse> customExtensionResponseList = customExtensionEntityList.stream()
                .map(this::toCustomExtensionResponse)
                .collect(Collectors.toList());

        return new ExtensionListResponse(
                fixedExtensionResponseList,
                customExtensionResponseList,
                customExtensionEntityList.size(),
                CUSTOM_EXTENSION_LIMIT
        );
    }
}
