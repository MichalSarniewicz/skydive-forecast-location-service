package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;

import java.util.List;

public interface GetAllDropzonesUseCase {

    List<DropzoneResponse> execute();
}
