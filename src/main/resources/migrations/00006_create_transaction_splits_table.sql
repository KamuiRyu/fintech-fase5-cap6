CREATE TABLE transaction_splits (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    transaction_id INTEGER NOT NULL,
    user_id INTEGER NOT NULL,
    amount NUMBER(12,4) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT transaction_splits_transactions_FK FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    CONSTRAINT transaction_splits_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);