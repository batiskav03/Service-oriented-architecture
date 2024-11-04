create table coordinate
(
    id UUID PRIMARY KEY,
    x BIGINT,
    y INT
);

create table person
(
    id UUID PRIMARY KEY,
    birthday DATE,
    passport_uuid UUID,
    hairColor TEXT,
    nationality TEXT
);

create table location
(
    id UUID PRIMARY KEY,
    x BIGINT,
    y FLOAT,
    z FLOAT,
    name TEXT
);

create table worker
(
    id UUID PRIMARY KEY,
    name TEXT,
    coordinates_id UUID REFERENCES coordinate (id),
    creation_date DATE,
    salary INT,
    start_date DATE,
    status TEXT,
    position TEXT,
    person_id UUID REFERENCES person (id)
);

insert into worker (id, name) values ('0b4cd6d5-5a8b-4aa7-8f4a-49b74a733a90', 'alex');

