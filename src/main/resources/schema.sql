DROP TABLE IF EXISTS member CASCADE;

CREATE TABLE member (
    id bigint GENERATED ALWAYS AS IDENTITY,
    username varchar(128),
    email varchar(128) UNIQUE,
    password varchar(128),
    PRIMARY KEY (id)
);

CREATE TABLE form (
    id bigint GENERATED ALWAYS AS IDENTITY,
    owner_id bigint,
    name varchar(128),
    phone_number varchar(128),
    email varchar(128),
    message varchar(1280),
    visualized boolean DEFAULT false,
    PRIMARY KEY (id),
    CONSTRAINT fk_owner_id FOREIGN KEY(owner_id) REFERENCES member(id)
);