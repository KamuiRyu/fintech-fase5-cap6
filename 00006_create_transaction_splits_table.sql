-- Tabela de usuários
CREATE TABLE users (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR2(100) NOT NULL,
    email VARCHAR2(100) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabela de transações
CREATE TABLE transactions (
    id INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id INTEGER NOT NULL,
    amount NUMBER(12,4) NOT NULL,
    description VARCHAR2(255),
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT transactions_users_FK FOREIGN KEY (user_id) REFERENCES users(id)
);

-- Tabela de divisões de transação
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

-- Índices para melhorar performance
CREATE INDEX idx_transaction_splits_transaction_id ON transaction_splits(transaction_id);
CREATE INDEX idx_transaction_splits_user_id ON transaction_splits(user_id);
CREATE INDEX idx_transactions_user_id ON transactions(user_id);
