CREATE TABLE categories (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100),
    description CLOB,
    type SMALLINT,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);