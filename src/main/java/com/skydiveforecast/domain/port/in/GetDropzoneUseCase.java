package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class GetDropzoneUseCase {

    private final DropzoneRepository dropzoneRepository;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    public DropzoneResponse execute(Long id) {
        DropzoneEntity dropzone = dropzoneRepository.findById(id)
                .orElseThrow(() -> new DropzoneNotFoundException("Dropzone with id " + id + " not found"));
        return dropzoneMapper.toResponse(dropzone);
    }
}
