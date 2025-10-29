package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.domain.model.DropzoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DropzoneJpaRepository extends JpaRepository<DropzoneEntity, Long> {

    List<DropzoneEntity> findByCity(String city);
}
