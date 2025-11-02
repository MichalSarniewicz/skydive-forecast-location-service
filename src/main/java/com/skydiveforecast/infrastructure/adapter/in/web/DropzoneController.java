package com.skydiveforecast.infrastructure.adapter.in.web;

import com.skydiveforecast.domain.model.Dropzone;
import com.skydiveforecast.domain.port.in.*;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneRequest;
import com.skydiveforecast.infrastructure.adapter.in.web.dto.DropzoneResponse;
import com.skydiveforecast.infrastructure.adapter.in.web.mapper.DropzoneMapper;
import com.skydiveforecast.infrastructure.security.annotation.PermissionSecurity;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/locations/dropzones")
@RequiredArgsConstructor
public class DropzoneController {

    private final CreateDropzoneUseCase createDropzoneUseCase;
    private final GetDropzoneUseCase getDropzoneUseCase;
    private final GetAllDropzonesUseCase getAllDropzonesUseCase;
    private final UpdateDropzoneUseCase updateDropzoneUseCase;
    private final DeleteDropzoneUseCase deleteDropzoneUseCase;
    private final FindDropzonesByCityUseCase findDropzonesByCityUseCase;
    private final DropzoneMapper mapper;

    @PostMapping
    @PermissionSecurity(permission = "DROPZONE_CREATE")
    @Operation(summary = "Create dropzone", description = "Creates dropzones", tags = {"Dropzones"})
    public ResponseEntity<DropzoneResponse> createDropzone(@Valid @RequestBody DropzoneRequest request) {
        Dropzone dropzone = createDropzoneUseCase.execute(mapper.toDomain(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(dropzone));
    }

    @GetMapping("/{id}")
    @PermissionSecurity(permission = "DROPZONE_VIEW")
    @Operation(summary = "Get dropzone", description = "Gets dropzone by id", tags = {"Dropzones"})
    public ResponseEntity<DropzoneResponse> getDropzone(@PathVariable Long id) {
        return ResponseEntity.ok(mapper.toResponse(getDropzoneUseCase.execute(id)));
    }

    @GetMapping
    @PermissionSecurity(permission = "DROPZONE_VIEW")
    @Operation(summary = "Get all dropzones", description = "Gets all dropzones", tags = {"Dropzones"})
    public ResponseEntity<List<DropzoneResponse>> getAllDropzones() {
        List<DropzoneResponse> responses = getAllDropzonesUseCase.execute().stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    @PermissionSecurity(permission = "DROPZONE_UPDATE")
    @Operation(summary = "Update dropzone", description = "Updates dropzone by id", tags = {"Dropzones"})
    public ResponseEntity<DropzoneResponse> updateDropzone(
            @PathVariable Long id,
            @Valid @RequestBody DropzoneRequest request) {
        Dropzone dropzone = updateDropzoneUseCase.execute(id, mapper.toDomain(request));
        return ResponseEntity.ok(mapper.toResponse(dropzone));
    }

    @DeleteMapping("/{id}")
    @PermissionSecurity(permission = "DROPZONE_DELETE")
    @Operation(summary = "Delete dropzone", description = "Deletes dropzone by id", tags = {"Dropzones"})
    public ResponseEntity<Void> deleteDropzone(@PathVariable Long id) {
        deleteDropzoneUseCase.execute(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/city/{city}")
    @PermissionSecurity(permission = "DROPZONE_VIEW")
    @Operation(summary = "Get dropzones by city", description = "Gets dropzones by city", tags = {"Dropzones"})
    public ResponseEntity<List<DropzoneResponse>> getDropzonesByCity(@PathVariable String city) {
        List<DropzoneResponse> responses = findDropzonesByCityUseCase.execute(city).stream()
                .map(mapper::toResponse)
                .toList();
        return ResponseEntity.ok(responses);
    }
}