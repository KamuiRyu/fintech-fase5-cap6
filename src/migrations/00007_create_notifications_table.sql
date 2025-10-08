CREATE TABLE notifications (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    type SMALLINT,
    content CLOB,
    read CHAR(1),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT notifications_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);