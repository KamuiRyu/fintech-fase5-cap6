CREATE TABLE card_members (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    card_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    can_manage CHAR(1),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT card_members_cards_FK FOREIGN KEY (card_id) REFERENCES cards(id),
    CONSTRAINT card_members_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);