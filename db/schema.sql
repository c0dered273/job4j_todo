CREATE TABLE IF NOT EXISTS users (
     id SERIAL PRIMARY KEY,
     name VARCHAR,
     email VARCHAR(320) NOT NULL UNIQUE,
     password VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS items (
    id SERIAL PRIMARY KEY,
    description TEXT,
    created TIMESTAMP,
    done BOOLEAN,
    user_id INT NOT NULL REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS categories (
    id SERIAL PRIMARY KEY,
    name VARCHAR
);