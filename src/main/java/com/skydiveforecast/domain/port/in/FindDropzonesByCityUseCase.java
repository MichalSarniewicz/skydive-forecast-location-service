package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.out.persistance.DropzoneRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FindDropzonesByCityUseCase {

    private final DropzoneRepository dropzoneRepository;
    private final DropzoneMapper dropzoneMapper;

    @Transactional(readOnly = true)
    public List<DropzoneResponse> execute(String city) {
        return dropzoneRepository.findByCity(city)
                .stream()
                .map(dropzoneMapper::toResponse)
                .collect(Collectors.toList());
    }
}