package com.disaster.repository;

import com.disaster.entity.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster, UUID> {

    /**
     * Finds disasters that contain a specific tag in their tags array.
     * This uses a native PostgreSQL query feature.
     * @param tag The tag to search for.
     * @return A list of matching disasters.
     */
    @Query("SELECT d FROM Disaster d WHERE :tag = ANY(d.tags)")
    List<Disaster> findByTag(@Param("tag") String tag);
} 