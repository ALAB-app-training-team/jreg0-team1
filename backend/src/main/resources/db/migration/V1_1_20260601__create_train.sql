CREATE TABLE train(
    id           VARCHAR(36) PRIMARY KEY,
    train_number VARCHAR(255),
    train_name   VARCHAR(255),
    route_id     VARCHAR(12),
    train_nickname VARCHAR(255),
    formation    INT,
    FOREIGN KEY (route_id) REFERENCES route (id)
);

