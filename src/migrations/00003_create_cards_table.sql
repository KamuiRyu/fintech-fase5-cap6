CREATE TABLE cards (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    issuer VARCHAR2(100),
    last_four_digits CHAR(4),
    alias VARCHAR2(100),
    shared CHAR(1),
    owner_id INTEGER NOT NULL,
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT cards_FK_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);