package com.emre.konumnot.repository;

import com.emre.konumnot.model.LocationNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationNoteRepository extends JpaRepository<LocationNote, Long> {

    @Query(value = "SELECT * FROM kn_location_notes WHERE user_id = :userId AND ST_DWithin(location\\:\\:geography, ST_SetSRID(ST_MakePoint(:longitude, :latitude), 4326)\\:\\:geography, :radiusInMeters)", nativeQuery = true)
    List<LocationNote> findNotesNearLocation(
        @Param("userId") Long userId,
        @Param("latitude") double latitude,
        @Param("longitude") double longitude,
        @Param("radiusInMeters") double radiusInMeters
    );

    List<LocationNote> findAllByUserId(Long userId);
}
