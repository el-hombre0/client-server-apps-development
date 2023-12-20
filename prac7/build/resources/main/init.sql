-- CREATE TABLE IF NOT EXISTS catsr (
--     id SERIAL PRIMARY KEY,
--     name VARCHAR(255),
--     color VARCHAR(255),
--     age INTEGER,
--     breed VARCHAR(255)
--     );

CREATE TABLE IF NOT EXISTS orders (
    id SERIAL PRIMARY KEY,
    client_name VARCHAR(255),
    client_phone VARCHAR(255),
    car_model VARCHAR(255),
    required_kilowatts INTEGER,
    distance_to_client BIGSERIAL,
    cost BIGSERIAL
    );

