CREATE TABLE IF NOT EXISTS booking
(
    id          BIGSERIAL PRIMARY KEY,
    place_id    BIGINT         NOT NULL,
    client_name VARCHAR(255)   NOT NULL,
    event_date  TIMESTAMP      NOT NULL,
    status      VARCHAR(50) DEFAULT 'PENDING',
    value       DECIMAL(10, 2) NOT NULL,
    package     VARCHAR(50)    NOT NULL,
    DATE_END    DATE       NOT NULL,

        CONSTRAINT fk_booking_place FOREIGN KEY (place_id)
            REFERENCES place (id)
);