--liquibase formatted sql
--changeset author:2025-10-16-add-dropzones-table

-- Create a schema if not exists
CREATE SCHEMA IF NOT EXISTS skydive_forecast_location;

CREATE TABLE IF NOT EXISTS skydive_forecast_location.dropzones
(
    id                   BIGSERIAL PRIMARY KEY,
    name                 VARCHAR(255) UNIQUE NOT NULL,
    city                 VARCHAR(255)        NOT NULL,
    latitude             DECIMAL(10, 8)      NOT NULL,
    longitude            DECIMAL(11, 8)      NOT NULL,
    is_wingsuit_friendly BOOLEAN             NOT NULL DEFAULT false
);

CREATE INDEX IF NOT EXISTS idx_dropzone_city ON skydive_forecast_location.dropzones (city);
CREATE INDEX IF NOT EXISTS idx_dropzone_coordinates ON skydive_forecast_location.dropzones (latitude, longitude);