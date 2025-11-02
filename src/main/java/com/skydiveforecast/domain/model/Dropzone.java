package com.skydiveforecast.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
@Builder
public class Dropzone {
    private final Long id;
    private final String name;
    private final String city;
    private final BigDecimal latitude;
    private final BigDecimal longitude;
    private final Boolean isWingsuitFriendly;
}
