create table coordinates
(
    id UUID PRIMARY KEY,
    x BIGINT,
    y INT
);

create table persons
(
    id UUID PRIMARY KEY,
    birthday DATE,
    passport_uuid UUID,
    hairColor TEXT,
    nationality TEXT
);

create table locations
(
    id UUID PRIMARY KEY,
    x BIGINT,
    y FLOAT,
    z FLOAT,
    name TEXT
);

create table users
(
    id UUID PRIMARY KEY,
    name TEXT,
    coordinates UUID REFERENCES coordinates (id),
    creationTime DATE,
    salary INT,
    startDate DATE,
    status TEXT,
    person UUID REFERENCES persons (id)
);


