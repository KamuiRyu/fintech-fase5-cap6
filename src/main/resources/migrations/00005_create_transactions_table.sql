CREATE TABLE transactions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    description CLOB,
    category_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    card_id INTEGER,
    amount NUMBER(12,4) NOT NULL,
    currency CHAR(3),
    occurred_at DATE NOT NULL,
    due_date DATE NOT NULL,
    is_recurring CHAR(1) DEFAULT 'N',
    is_paid CHAR(1) DEFAULT 'N',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT transactions_cards_FK FOREIGN KEY (card_id) REFERENCES cards(id),
    CONSTRAINT transactions_categories_FK FOREIGN KEY (category_id) REFERENCES categories(id),
    CONSTRAINT transactions_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);
