"CONSTRAINT transactions_users_FK FOREIGN KEY (user_id) REFERENCES users(id)" +
        ")",
        "CREATE TABLE transaction_splits (" +
        "id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY," +
        "transaction_id INTEGER NOT NULL," +
        "user_id INTEGER NOT NULL," +
        "amount NUMBER(12,4) NOT NULL CHECK (amount >= 0)," +
        "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
        "CONSTRAINT transaction_splits_transactions_FK FOREIGN KEY (transaction_id) REFERENCES transactions(id)," +
        "CONSTRAINT transaction_splits_users_FK FOREIGN KEY (user_id) REFERENCES users(id)" +
        ")",
        "CREATE INDEX idx_transaction_splits_transaction_id ON transaction_splits(transaction_id);",
        "CREATE INDEX idx_transaction_splits_user_id ON transaction_splits(user_id);",
        "CREATE INDEX idx_transactions_user_id ON transactions(user_id);",
        "CREATE OR REPLACE TRIGGER trg_transaction_splits_update " +
        "BEFORE UPDATE ON transaction_splits " +
        "FOR EACH ROW " +
        "BEGIN " +
        "    :NEW.updated_at := CURRENT_TIMESTAMP; " +
        "END; " +
        "/",
        "CREATE VIEW view_transaction_splits_total AS " +
        "SELECT transaction_id, SUM(amount) as total_amount " +
        "FROM transaction_splits " +
        "GROUP BY transaction_id;"
        };
        try (Statement stmt = connection.createStatement()) {
        for (String sql : sqlScripts) {
        stmt.addBatch(sql);
            }
                    stmt.executeBatch();
        }
                }
                }