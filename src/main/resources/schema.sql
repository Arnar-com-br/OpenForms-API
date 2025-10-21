DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member (
    id bigint GENERATED ALWAYS AS IDENTITY,
    username varchar(128),
    email varchar(128) UNIQUE,
    password varchar(64),
    PRIMARY KEY (id)
);

