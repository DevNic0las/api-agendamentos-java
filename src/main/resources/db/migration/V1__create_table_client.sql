CREATE TABLE client
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(150) NOT NULL,
    email    VARCHAR(255) NOT NULL unique,
    password VARCHAR(255) NOT NULL
);

