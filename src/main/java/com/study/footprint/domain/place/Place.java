package com.study.footprint.domain.place;

import com.study.footprint.common.converter.common.CityTypeCd;
import com.study.footprint.domain.BaseEntity;
import com.study.footprint.domain.posting.Posting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String address;

    private Double latitude;

    private Double longitude;

    private CityTypeCd cityTypeCd;

    private Boolean isPosting;

    @OneToMany(mappedBy = "place", cascade = CascadeType.ALL)
    private List<Posting> postings = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.isPosting = this.isPosting == null ? true : isPosting;
    }

    @Builder
    public Place(Long id, String name, String address, Double latitude, Double longitude, CityTypeCd cityTypeCd, Boolean isPosting) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.cityTypeCd = cityTypeCd;
        this.isPosting = isPosting;
    }
}
