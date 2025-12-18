CREATE TABLE IF NOT EXISTS users (
    username VARCHAR(50) NOT NULL PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    enabled TINYINT(1) NOT NULL
);

CREATE TABLE IF NOT EXISTS authorities (
    username VARCHAR(50) NOT NULL,
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY(username) REFERENCES users(username),
    UNIQUE KEY ix_auth_username (username, authority)
);
