ALTER TABLE train
    ADD CONSTRAINT fk_train_route
        FOREIGN KEY (route_id)
            REFERENCES route (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE car
    ADD CONSTRAINT fk_car_train
        FOREIGN KEY (train_id)
            REFERENCES train (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE seat
    ADD CONSTRAINT fk_seat_car
        FOREIGN KEY (car_id)
            REFERENCES car (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE schedule
    ADD CONSTRAINT fk_schedule_train
        FOREIGN KEY (train_id)
            REFERENCES train (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE schedule
    ADD CONSTRAINT fk_schedule_schedule
        FOREIGN KEY (schedule_id)
            REFERENCES station (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE reservation
    ADD CONSTRAINT fk_reservation_train
        FOREIGN KEY (train_id)
            REFERENCES train (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE reservation
    ADD CONSTRAINT fk_reservation_seat
        FOREIGN KEY (seat_id)
            REFERENCES seat (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE reservation
    ADD CONSTRAINT fk_reservation_station
        FOREIGN KEY (boarding_station_id)
            REFERENCES station (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE reservation
    ADD CONSTRAINT fk_reservation_station
        FOREIGN KEY (destination_station_id)
            REFERENCES station (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE stopstation
    ADD CONSTRAINT fk_stopstation_route
        FOREIGN KEY (route_id)
            REFERENCES route (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

ALTER TABLE stopstation
    ADD CONSTRAINT fk_stopstation_station
        FOREIGN KEY (station_id)
            REFERENCES station (id)
            ON UPDATE CASCADE
            ON DELETE SET NULL;

