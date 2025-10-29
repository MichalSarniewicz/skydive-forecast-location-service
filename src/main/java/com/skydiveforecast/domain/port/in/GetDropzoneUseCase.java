package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;

public interface GetDropzoneUseCase {

    DropzoneResponse execute(Long id);
}
