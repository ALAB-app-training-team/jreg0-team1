ALTER TABLE reservation
ALTER COLUMN id TYPE uuid
USING id::uuid;
