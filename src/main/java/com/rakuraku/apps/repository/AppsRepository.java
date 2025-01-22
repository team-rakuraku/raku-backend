package com.rakuraku.apps.repository;

import com.rakuraku.apps.entity.Apps;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface AppsRepository extends JpaRepository<Apps, Long> {
    Optional<Apps> findByAppId(String appId);
}
