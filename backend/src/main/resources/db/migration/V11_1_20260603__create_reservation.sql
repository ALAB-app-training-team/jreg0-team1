CREATE TABLE reservation(
    id         VARCHAR(36) PRIMARY KEY,
    seat_id   VARCHAR(36),
    date DATE,
    train_id VARCHAR(36),
    boarding_station_id VARCHAR(36),
    Destination_station_id VARCHAR(36),
    payment_method VARCHAR(36),
    payment_status VARCHAR(2),
    fee INT,
    account_id VARCHAR(36),
    FOREIGN KEY (seat_id) REFERENCES seat (id),
    FOREIGN KEY (train_id) REFERENCES train (id),
    FOREIGN KEY (boarding_station_id) REFERENCES station (id),
    FOREIGN KEY (Destination_station_id) REFERENCES station (id)
);