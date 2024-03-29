DROP TABLE IF EXISTS mpa_type,
					 genre_type CASCADE;

DROP ALL OBJECTS;

CREATE TABLE IF NOT EXISTS mpa_type(
rating_mpa_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name varchar
);

CREATE TABLE IF NOT EXISTS genre_type(
genre_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name varchar NOT NULL
);

CREATE TABLE IF NOT EXISTS users (
user_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
email varchar NOT NULL,
login varchar,
name varchar,
birthday date
);

CREATE TABLE IF NOT EXISTS friends(
id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
user_id INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
friend_id INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE,
status varchar
);

CREATE TABLE IF NOT EXISTS films(
film_id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
name varchar NOT NULL,
description varchar NOT NULL,
release_date timestamp NOT NULL,
duration INTEGER,
rating_mpa_id INTEGER
);

CREATE TABLE IF NOT EXISTS likes(
like_id integer GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
film_id INTEGER NOT NULL REFERENCES films (film_id) ON DELETE CASCADE,
user_id INTEGER NOT NULL REFERENCES users (user_id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS genre(
id INTEGER GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
film_id INTEGER REFERENCES films (film_id) ON DELETE CASCADE,
genre_id INTEGER REFERENCES genre_type (genre_id)
);

