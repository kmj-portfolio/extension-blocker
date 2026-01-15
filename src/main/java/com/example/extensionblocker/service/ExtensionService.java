package com.example.extensionblocker.service;

import com.example.extensionblocker.dto.request.BlockFixedExtensionRequest;
import com.example.extensionblocker.dto.request.CreateCustomExtensionRequest;
import com.example.extensionblocker.dto.response.*;
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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Transactional(readOnly = true)
    public BlockedExtensionResponse getBlockedExtensions() {

        List<FixedExtensionEntity> fixedExtensionEntityList = fixedExtensionRepository.findByBlockedIsTrue();
        List<CustomExtensionEntity> customExtensionEntityList = customExtensionRepository.findAll();

        return extensionMapper.toBlockedExtensionResponse(fixedExtensionEntityList, customExtensionEntityList);
    }

    @Transactional(readOnly = true)
    public CustomExtensionListResponse getCustomExtensionList() {

        List<CustomExtensionEntity> customExtensionEntityList = customExtensionRepository.findAll();
        return extensionMapper.toCustomExtensionListResponse(customExtensionEntityList);
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
    public FixedExtensionListResponse changeFixedExtensionStatus(BlockFixedExtensionRequest request) {

        List<Integer> blockedList = request.blockedFixedExtensionList();
        if (blockedList == null) {
            blockedList = List.of();
        }

        Set<Integer> requestedIdSet;

        if (blockedList.isEmpty()) {
            requestedIdSet =  new HashSet<>();
        } else {
            // 차단 요청된 ID들이 존재하는 지 검증
            requestedIdSet = validateRequestedIds(blockedList);
        }

        // 모든 고정 확장자 조회
        List<FixedExtensionEntity> fixedExtensionEntityList = fixedExtensionRepository.findAll();

        // 고정 확장자가 차단 요청 목록에 있으면 block, 없으면 unblock
        for (FixedExtensionEntity fixedExtension : fixedExtensionEntityList) {

            boolean blockRequested = requestedIdSet.contains(fixedExtension.getId());

            if (blockRequested) {
                fixedExtension.block();
            } else {
                fixedExtension.unblock();
            }
        }
        return extensionMapper.toFixedExtensionListResponse(fixedExtensionEntityList);
    }


    @Transactional
    public void deleteCustomExtension(Integer id) {

        if (!customExtensionRepository.existsById(id)) {
            throw new ExtensionException(ErrorCode.EXTENSION_DOES_NOT_EXIST);
        }
        customExtensionRepository.deleteById(id);
    }

    // 존재하지 않는 확장자가 request에 포함되어 있는지 확인
    private Set<Integer> validateRequestedIds(List<Integer> requestedIds) {

        Set<Integer> requestedIdSet = new HashSet<>(requestedIds);
        List<FixedExtensionEntity> foundFixedEntityList = fixedExtensionRepository.findAllById(requestedIds);

        // 요청된 ID 수 중 DB에 존재하는 ID의 개수를 확인
        if (foundFixedEntityList.size() != requestedIds.size()) {
            throw new ExtensionException(ErrorCode.EXTENSION_DOES_NOT_EXIST);
        }

        return requestedIdSet;
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
                    "확장자는 영문과 숫자, '-'와 '_'로만 입력해주세요.");
        }
    }
}
