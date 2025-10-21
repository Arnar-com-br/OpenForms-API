DROP TABLE IF EXISTS member CASCADE;
DROP TABLE IF EXISTS email CASCADE;

CREATE TABLE member (
    id bigint GENERATED ALWAYS AS IDENTITY,
    username varchar(128),
    email varchar(128) UNIQUE,
    password varchar(64),
    PRIMARY KEY (id)
);

CREATE TABLE email (
    id bigint GENERATED ALWAYS AS IDENTITY,
    email varchar(255) UNIQUE,
    PRIMARY KEY (id)
);