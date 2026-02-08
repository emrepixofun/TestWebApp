-- PostGIS extension required for geometry(Point,4326) and ST_* functions.
-- Flyway runs before Hibernate, so the geometry type exists when tables are created.
CREATE EXTENSION IF NOT EXISTS postgis;
