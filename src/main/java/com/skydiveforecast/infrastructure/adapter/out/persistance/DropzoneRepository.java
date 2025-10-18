package com.skydiveforecast.infrastructure.adapter.out.persistance;

import com.skydiveforecast.domain.model.DropzoneEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DropzoneRepository extends JpaRepository<DropzoneEntity, Long> {

    List<DropzoneEntity> findByCity(String city);
}
