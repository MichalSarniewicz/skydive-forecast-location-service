package com.skydiveforecast.infrastructure.persistance.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(
        name = "dropzones",
        schema = "skydive_forecast_location",
        indexes = {
                @Index(name = "idx_dropzone_city", columnList = "city"),
                @Index(name = "idx_dropzone_coordinates", columnList = "latitude,longitude")
        }
)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DropzoneEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name is required")
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "City is required")
    @Column(nullable = false)
    private String city;

    @NotNull(message = "Latitude is required")
    @Column(nullable = false, precision = 10, scale = 8)
    private BigDecimal latitude;

    @NotNull(message = "Longitude is required")
    @Column(nullable = false, precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "is_wingsuit_friendly", nullable = false)
    @Builder.Default
    private Boolean isWingsuitFriendly = false;
}