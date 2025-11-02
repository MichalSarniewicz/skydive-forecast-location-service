package com.skydiveforecast.domain.port.in;

import com.skydiveforecast.domain.model.Dropzone;

import java.util.List;

public interface FindDropzonesByCityUseCase {

    List<Dropzone> execute(String city);
}
