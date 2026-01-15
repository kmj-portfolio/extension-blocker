package com.example.extensionblocker.util;

import com.example.extensionblocker.entity.FixedExtensionEntity;
import com.example.extensionblocker.repository.FixedExtensionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FixedExtensionDataInitializer implements CommandLineRunner {

    private final FixedExtensionRepository fixedExtensionRepository;

    @Override
    public void run(String... args) throws Exception {
        List<String> fixed = List.of("bat", ".cmd", "com", "cpl", "exe", "scr", "js");

        Set<String> existingFixedExtensionNames = fixedExtensionRepository.findAll().stream()
                .map(FixedExtensionEntity::getExtensionName)
                .collect(Collectors.toSet());

        for (String s : fixed) {
            if (!existingFixedExtensionNames.contains(s)) {
                fixedExtensionRepository.save(new FixedExtensionEntity(s));
            }
        }
    }
}
