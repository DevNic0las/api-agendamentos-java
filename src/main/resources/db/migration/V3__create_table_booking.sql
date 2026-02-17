
CREATE TYPE booking_status_type AS ENUM ('PENDING', 'CONFIRMED', 'CANCELLED', 'EXPIRED');

CREATE TABLE IF NOT EXISTS booking
(
    id             BIGSERIAL PRIMARY KEY,
    place_id       BIGINT         NOT NULL,
    client_name    VARCHAR(255)   NOT NULL,
    event_date     TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,

    booking_status  booking_status_type DEFAULT 'PENDING',
    value          DECIMAL(10, 2) NOT NULL,
    party_package  VARCHAR(50)    NOT NULL,
    date_end       TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP           NOT NULL,

    CONSTRAINT fk_booking_place FOREIGN KEY (place_id) REFERENCES place (id)
    );