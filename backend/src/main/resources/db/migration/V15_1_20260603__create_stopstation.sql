CREATE TABLE stopstation(
    route_id         VARCHAR(36),
    station_id   VARCHAR(36),
    PRIMARY KEY (route_id, station_id),
    FOREIGN KEY (route_id) REFERENCES route (id),
    FOREIGN KEY (station_id) REFERENCES station (id)
);

