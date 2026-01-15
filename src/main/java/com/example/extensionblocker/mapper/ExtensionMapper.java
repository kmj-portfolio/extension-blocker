package com.example.extensionblocker.mapper;

import com.example.extensionblocker.dto.response.*;
import com.example.extensionblocker.entity.CustomExtensionEntity;
import com.example.extensionblocker.entity.FixedExtensionEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ExtensionMapper {

    // 커스텀 확장자는 최대 200개까지 허용
    private static final int CUSTOM_EXTENSION_LIMIT = 200;

    // 고정 확장자와 커스텀 확장자를 분리해서 반환
    public ExtensionListResponse toExtensionListResponse(List<FixedExtensionEntity> fixedExtensionEntityList,
                                                         List<CustomExtensionEntity> customExtensionEntityList) {

        List<FixedExtensionResponse> fixedExtensionResponseList = fixedExtensionEntityList.stream()
                .map(this::toFixedExtensionResponse)
                .toList();

        List<CustomExtensionResponse> customExtensionResponseList = customExtensionEntityList.stream()
                .map(this::toCustomExtensionResponse)
                .toList();

        return new ExtensionListResponse(
                fixedExtensionResponseList,
                customExtensionResponseList,
                customExtensionEntityList.size(),
                CUSTOM_EXTENSION_LIMIT
        );
    }

    // 차단된 고정 확장자와 커스텀 확장자를 하나의 목록으로 합쳐 반환
    public BlockedExtensionResponse toBlockedExtensionResponse(List<FixedExtensionEntity> fixedExtensionEntityList,
                                                               List<CustomExtensionEntity> customExtensionEntityList) {

        List<String> fixed = fixedExtensionEntityList.stream()
                .map(FixedExtensionEntity::getExtensionName)
                .toList();

        List<String> custom = customExtensionEntityList.stream()
                .map(CustomExtensionEntity::getExtensionName)
                .toList();

        List<String> result = new ArrayList<>();
        result.addAll(fixed);
        result.addAll(custom);

        return new BlockedExtensionResponse(result);
    }

    public CustomExtensionListResponse toCustomExtensionListResponse(List<CustomExtensionEntity> customExtensionEntityList) {

        List<CustomExtensionResponse> customExtensionResponseList = customExtensionEntityList.stream()
                .map(this::toCustomExtensionResponse)
                .toList();

        return new CustomExtensionListResponse(customExtensionResponseList);
    }

    public CustomExtensionResponse toCustomExtensionResponse(CustomExtensionEntity customExtension) {

        return new CustomExtensionResponse(
                customExtension.getId(),
                customExtension.getExtensionName()
        );
    }

    public FixedExtensionListResponse toFixedExtensionListResponse(List<FixedExtensionEntity> fixedExtensionEntityList) {

        List<FixedExtensionResponse> fixedExtensionResponses = fixedExtensionEntityList.stream()
                .map(this::toFixedExtensionResponse)
                .toList();

        return new FixedExtensionListResponse(fixedExtensionResponses);
    }

    public FixedExtensionResponse toFixedExtensionResponse(FixedExtensionEntity fixedExtension) {
        return new FixedExtensionResponse(
                fixedExtension.getId(),
                fixedExtension.getExtensionName(),
                fixedExtension.isBlocked()
        );
    }
}
