package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.Dropzone;

public interface UpdateDropzoneUseCase {

    Dropzone execute(Long id, Dropzone dropzone);
}
