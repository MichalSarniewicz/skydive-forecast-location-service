-- Create a schema for the application
CREATE SCHEMA IF NOT EXISTS "skydive-forecast-location";

-- Grant privileges to the application location
GRANT ALL PRIVILEGES ON SCHEMA "skydive-forecast-location" TO skydive_forecast_location;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA "skydive-forecast-location" TO skydive_forecast_location;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA "skydive-forecast-location" TO skydive_forecast_location;

-- Set default privileges for future tables
ALTER DEFAULT PRIVILEGES IN SCHEMA "skydive-forecast-location" GRANT ALL PRIVILEGES ON TABLES TO skydive_forecast_location;
ALTER DEFAULT PRIVILEGES IN SCHEMA "skydive-forecast-location" GRANT ALL PRIVILEGES ON SEQUENCES TO skydive_forecast_location;
