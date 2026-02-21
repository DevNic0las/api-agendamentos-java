CREATE TABLE client
(
    id       BIGSERIAL PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    phone_number     VARCHAR(30) NOT NULL,
    date_of_birth  DATE NOT NULL ,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

