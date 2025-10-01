CREATE TABLE IF NOT EXISTS rbac (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    path VARCHAR(255) NOT NULL,
    method VARCHAR(10) NOT NULL
);

-- Örnek veri
INSERT INTO rbac (email, path, method) VALUES ('user@example.com', '/api/v1/orders', 'GET');
INSERT INTO rbac (email, path, method) VALUES ('user@example.com', '/api/v1/orders', 'POST');