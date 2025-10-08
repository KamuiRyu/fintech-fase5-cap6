CREATE TABLE transactions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description CLOB,
    category_id INTEGER,
    user_id INTEGER,
    card_id INTEGER,
    amount NUMBER(12,4),
    currency CHAR(3),
    occurred_at DATE,
    due_date DATE,
    is_recurring CHAR(1),
    is_paid CHAR(1),
    created_at TIMESTAMP,
    updated_at TIMESTAMP,
    CONSTRAINT transactions_cards_FK FOREIGN KEY (card_id) REFERENCES cards(id),
    CONSTRAINT transactions_categories_FK FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT transactions_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);
