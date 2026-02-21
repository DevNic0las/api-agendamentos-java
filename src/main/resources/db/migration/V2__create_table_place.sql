CREATE TABLE IF NOT EXISTS place
(
    id       BIGSERIAL PRIMARY KEY,
    name     varchar(100) not null,
    capacity int          NOT NULL,
    address  VARCHAR(255) NOT NULL
);