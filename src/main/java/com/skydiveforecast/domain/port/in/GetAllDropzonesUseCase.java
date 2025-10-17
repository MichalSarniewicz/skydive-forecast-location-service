package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GetAllDropzonesUseCase {

    private final DropzoneRepository dropzoneRepository;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    public List<DropzoneResponse> execute() {
        return dropzoneRepository.findAll()
                .stream()
                .map(dropzoneMapper::toResponse)
                .collect(Collectors.toList());
    }
}
