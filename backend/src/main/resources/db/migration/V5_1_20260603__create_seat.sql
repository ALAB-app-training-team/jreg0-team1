CREATE TABLE seat(
    id         VARCHAR(36) PRIMARY KEY,
    car_id   VARCHAR(36),
    seat_location        VARCHAR(12),
    FOREIGN KEY (car_id) REFERENCES car (id)
);

