package com.disaster.repository;

import com.disaster.entity.Resource;
import org.locationtech.jts.geom.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, UUID> {

    /**
     * Finds resources within a given distance from a geographic point.
     * Note: The 'dwithin' function is specific to Hibernate Spatial with PostGIS.
     * The distance is in meters.
     * @param point The center point for the search.
     * @param distance The radius of the search in meters.
     * @return A list of matching resources.
     */
    @Query("SELECT r FROM Resource r WHERE dwithin(r.location, :point, :distance, true) = true")
    List<Resource> findResourcesWithinDistance(@Param("point") Point point, @Param("distance") double distance);

    /**
     * Finds resources for a specific disaster.
     */
    List<Resource> findByDisasterId(UUID disasterId);

    /**
     * Finds resources near a location for a specific disaster.
     */
    @Query("SELECT r FROM Resource r WHERE r.disaster.id = :disasterId AND dwithin(r.location, :point, :distance, true) = true")
    List<Resource> findResourcesNearLocation(@Param("disasterId") UUID disasterId, @Param("point") Point point, @Param("distance") double distance);

    /**
     * Finds nearby resources regardless of disaster.
     */
    @Query("SELECT r FROM Resource r WHERE dwithin(r.location, :point, :distance, true) = true")
    List<Resource> findNearbyResources(@Param("point") Point point, @Param("distance") double distance);
} 