CREATE TABLE IF NOT EXISTS parfum_store (
                                            id SERIAL,
                                            name VARCHAR(255) NOT NULL,
                                            description TEXT,
                                            type VARCHAR(255),
                                            price float(53) NOT NULL,
                                            weight float(53) NOT NULL
);