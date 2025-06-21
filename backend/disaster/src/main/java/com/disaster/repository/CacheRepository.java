package com.disaster.repository;

import com.disaster.entity.Cache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheRepository extends JpaRepository<Cache, String> {
} 