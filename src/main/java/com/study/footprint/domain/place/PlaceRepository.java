package com.study.footprint.domain.place;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    Optional<Place> findByLatitudeAndLongitude(Double latitude, Double longitude);
}
