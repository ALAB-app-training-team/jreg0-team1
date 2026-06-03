CREATE TABLE stopstation(
    route_id         VARCHAR(36) PRIMARY KEY,
    station_id   VARCHAR(36) PRIMARY KEY,
    FOREIGN KEY (route_id) REFERENCES route (id),
    FOREIGN KEY (station_id) REFERENCES station (id)
);