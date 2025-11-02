package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.Dropzone;

public interface CreateDropzoneUseCase {

    Dropzone execute(Dropzone dropzone);
}
