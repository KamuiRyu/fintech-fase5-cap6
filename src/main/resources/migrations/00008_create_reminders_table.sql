CREATE TABLE reminders (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    transaction_id INTEGER NOT NULL,
    reminded_at DATE,
    notified CHAR(1),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT reminders_transactions_FK FOREIGN KEY (transaction_id) REFERENCES transactions(id),
    CONSTRAINT reminders_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);