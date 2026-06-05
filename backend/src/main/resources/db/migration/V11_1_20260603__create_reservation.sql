CREATE TABLE reservation(
    id         VARCHAR(36) PRIMARY KEY,
    seat_id   VARCHAR(36),
    reservation_date DATE,
    train_id VARCHAR(36),
    boarding_station_id VARCHAR(36),
    destination_station_id VARCHAR(36),
    payment_method VARCHAR(36),
    payment_status VARCHAR(24),
    fee INT,
    account_id VARCHAR(36)
);

