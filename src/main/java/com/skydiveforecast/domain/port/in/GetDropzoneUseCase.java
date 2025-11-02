package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.Dropzone;

public interface GetDropzoneUseCase {

    Dropzone execute(Long id);
}
