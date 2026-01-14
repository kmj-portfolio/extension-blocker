package com.example.extensionblocker.repository;

import com.example.extensionblocker.entity.FixedExtensionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FixedExtensionRepository extends JpaRepository<FixedExtensionEntity, Integer> {

    boolean existsByExtensionName(String name);

    List<FixedExtensionEntity> findByBlockedIsTrue();
}
