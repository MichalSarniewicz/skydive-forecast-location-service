package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.domain.model.DropzoneEntity;
import com.skydiveforecast.domain.model.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateDropzoneUseCase {

    private final DropzoneRepository dropzoneRepository;
    private final DropzoneMapper dropzoneMapper;

    @Transactional
    public DropzoneResponse execute(Long id, DropzoneRequest request) {
        DropzoneEntity dropzone = dropzoneRepository.findById(id)
                .orElseThrow(() -> new DropzoneNotFoundException("Dropzone with id " + id + " not found"));

        dropzone.setName(request.getName());
        dropzone.setCity(request.getCity());
        dropzone.setLatitude(request.getLatitude());
        dropzone.setLongitude(request.getLongitude());
        dropzone.setIsWingsuitFriendly(request.getIsWingsuitFriendly());
        
        DropzoneEntity updatedDropzone = dropzoneRepository.save(dropzone);
        return dropzoneMapper.toResponse(updatedDropzone);
    }
}