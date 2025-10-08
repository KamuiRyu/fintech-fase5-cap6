CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    email VARCHAR2(200) UNIQUE,
    password VARCHAR2(300),
    name VARCHAR2(200),
    doc VARCHAR2(50),
    phone VARCHAR2(20),
    type SMALLINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);