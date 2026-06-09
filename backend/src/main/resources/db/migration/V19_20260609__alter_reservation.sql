ALTER TABLE reservation RENAME COLUMN boarding_station_id TO departure_station_id;

ALTER TABLE reservation
ADD COLUMN departure_date DATE;

