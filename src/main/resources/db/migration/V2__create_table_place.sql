CREATE TABLE IF NOT EXISTS place
(
    id       BIGSERIAL PRIMARY KEY,
    name     varchar(100) not null,
    capacity int          NOT NULL,
    addrees  VARCHAR(255) NOT NULL
);