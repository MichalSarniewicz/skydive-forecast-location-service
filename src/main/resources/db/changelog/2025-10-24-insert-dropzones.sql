--liquibase formatted sql
--changeset 2025-10-24-insert-dropzones splitStatements:false

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Chrcynno', 'Chrcynno', 52.57400000, 20.87194444, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Piotrków Trybunalski', 'Piotrków Trybunalski', 51.38258333, 19.68852778, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Włocławek', 'Włocławek', 52.58470278, 19.01560000, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Przasnysz', 'Przasnysz', 53.01105556, 20.93369444, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Gdańsk', 'Gdańsk', 54.25180556, 18.67091667, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Kraków', 'Kraków', 50.08966111, 20.20165833, true);

INSERT INTO skydive_forecast_location.dropzones
(name, city, latitude, longitude, is_wingsuit_friendly)
VALUES
    ('Skydive Nowy Targ', 'Nowy Targ', 49.46283333, 20.05066667, true);
