create table coordinates
(
    coordinates_uuid UUID PRIMARY KEY,
    x BIGINT,
    y INT
);

create table persons
(
    person_uuid UUID PRIMARY KEY,
    birthday DATE,
    passport_uuid UUID,
    hairColor TEXT,
    nationality TEXT
);

create table locations
(
    location_uuid UUID PRIMARY KEY,
    x BIGINT,
    y FLOAT,
    z FLOAT,
    name TEXT
);

create table users
(
    user_uuid UUID PRIMARY KEY,
    name TEXT,
    coordinates UUID REFERENCES coordinates (coordinates_uuid),
    creationTime DATE,
    salary INT,
    startDate DATE,
    status TEXT,
    person UUID REFERENCES persons (person_uuid)
);


