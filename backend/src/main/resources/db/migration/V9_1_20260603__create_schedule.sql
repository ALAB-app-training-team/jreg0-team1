CREATE TABLE schedule(
    id         VARCHAR(36) PRIMARY KEY,
    train_id VARCHAR(36),
    station_id VARCHAR(36),
    departure_time time,
    arrival_time time,
    departure_track INT,
    departure_date date,
    FOREIGN KEY (train_id) REFERENCES train (id),
    FOREIGN KEY (station_id) REFERENCES station (id)
);

