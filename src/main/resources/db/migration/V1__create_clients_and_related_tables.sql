CREATE TABLE addresses
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    street       VARCHAR(255) NOT NULL,
    number       VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    city         VARCHAR(255) NOT NULL,
    state        VARCHAR(255) NOT NULL,
    zip          VARCHAR(255) NOT NULL
);

CREATE TABLE contacts
(
    id    BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(255) NOT NULL
);

CREATE TABLE clients
(
    id            VARCHAR(36)  NOT NULL PRIMARY KEY,
    cnpj          VARCHAR(255) NOT NULL,
    address_id    BIGINT,
    contact_id    BIGINT,
    company_name   VARCHAR(255) NOT NULL,
    company_branch VARCHAR(255) NOT NULL,
    company_sector VARCHAR(255) NOT NULL,
    FOREIGN KEY (address_id) REFERENCES addresses (id),
    FOREIGN KEY (contact_id) REFERENCES contacts (id)

);
