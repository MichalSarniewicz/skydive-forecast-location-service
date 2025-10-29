package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;

public interface CreateDropzoneUseCase {

    DropzoneResponse execute(DropzoneRequest request);
}
