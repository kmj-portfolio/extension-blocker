package com.example.extensionblocker.repository;

import com.example.extensionblocker.entity.CustomExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomExtensionRepository extends JpaRepository<CustomExtensionEntity, Integer> {

    boolean existsByExtensionName(String extensionName);

}

