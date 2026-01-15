package com.example.extensionblocker.service;

import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.CustomExtensionResponse;
import com.example.extensionblocker.dto.response.ExtensionListResponse;
import com.example.extensionblocker.entity.CustomExtensionEntity;
import com.example.extensionblocker.entity.FixedExtensionEntity;
import com.example.extensionblocker.exception.ErrorCode;
import com.example.extensionblocker.exception.ExtensionException;
import com.example.extensionblocker.mapper.ExtensionMapper;
import com.example.extensionblocker.repository.CustomExtensionRepository;
import com.example.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExtensionService {

    private final FixedExtensionRepository fixedExtensionRepository;
    private final CustomExtensionRepository customExtensionRepository;
    private final ExtensionMapper extensionMapper;

    @Transactional(readOnly = true)
    public ExtensionListResponse getAllExtensions() {
        List<FixedExtensionEntity> fixedExtensionEntityList = fixedExtensionRepository.findAll();
        List<CustomExtensionEntity> customExtensionEntityList = customExtensionRepository.findAll();

        return extensionMapper.toExtensionListResponse(fixedExtensionEntityList, customExtensionEntityList);
    }

    @Transactional
    public CustomExtensionResponse createCustomExtension(CreateCustomExtensionRequest request) {

        // 현재 커스텀 확장자가 200개 미만인지 확인
        if (customExtensionRepository.count() >= 200) {
            throw new ExtensionException(ErrorCode.MAX_CUSTOM_EXTENSION_EXCEEDED);
        }

        // 입력된 확장자 이름 정규화 후 숫자와 알파벳만 포함하는 지 확인
        String extensionName = normalize(request.extensionName());
        validate(extensionName);

        // 중복되는 확장자 이름인지 확인
        if (customExtensionRepository.existsByExtensionName(extensionName)) {
            throw new ExtensionException(ErrorCode.DUPLICATE_EXTENSION);
        }

        CustomExtensionEntity customExtension = new CustomExtensionEntity(extensionName);

        customExtensionRepository.save(customExtension);

        return extensionMapper.toCustomExtensionResponse(customExtension);
    }

    @Transactional
    public void deleteCustomExtension(Integer id) {

        if (!customExtensionRepository.existsById(id)) {
            throw new ExtensionException(ErrorCode.EXTENSION_DOES_NOT_EXIST);
        }
        customExtensionRepository.deleteById(id);
    }

    private String normalize(String value) {
        return value.trim()
                .toLowerCase()
                .replaceFirst("^\\.+", "");
    }

    private void validate(String value) {
        if (!value.matches("^[a-zA-Z0-9_-]+$")) {
            throw new ExtensionException(
                    ErrorCode.ILLEGAL_ARGUMENT_EXCEPTION,
                    "확장자는 영문과 숫자로만 입력해주세요.");
        }
    }
}
