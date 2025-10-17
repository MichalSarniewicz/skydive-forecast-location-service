package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.exception.DropzoneNotFoundException;
import com.skydiveforecast.infrastructure.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeleteDropzoneUseCase {

    private final DropzoneRepository dropzoneRepository;

    @Transactional
    public void execute(Long id) {
        if (!dropzoneRepository.existsById(id)) {
            throw new DropzoneNotFoundException("Dropzone with id " + id + " not found");
        }
        dropzoneRepository.deleteById(id);
    }
}