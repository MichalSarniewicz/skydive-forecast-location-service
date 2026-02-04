package com.skydiveforecast.infrastructure.adapter.out.persistence;

import com.skydiveforecast.infrastructure.persistance.entity.DropzoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

interface DropzoneJpaRepository extends JpaRepository<DropzoneEntity, Long> {

    List<DropzoneEntity> findByCity(String city);
}
