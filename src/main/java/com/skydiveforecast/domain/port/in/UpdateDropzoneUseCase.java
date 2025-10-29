package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;

public interface UpdateDropzoneUseCase {

    DropzoneResponse execute(Long id, DropzoneRequest request);
}
