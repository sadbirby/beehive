CREATE TABLE users
(
    id                 VARCHAR(255)                 NOT NULL,
    created_by         VARCHAR(255)                 NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
    last_modified_by   VARCHAR(255)                 NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
    version            INTEGER                      NOT NULL,
    email              VARCHAR(255)                 NOT NULL,
    username           VARCHAR(255)                 NOT NULL,
    pre_computed_hash  VARCHAR(255)                 NOT NULL,
    account_status     VARCHAR(255)                 NOT NULL,
    profile_category   VARCHAR(255)                 NOT NULL,
    bio                VARCHAR(255),
    avatar_url         VARCHAR(2048),
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users ADD CONSTRAINT unique_users_email     UNIQUE (email);
ALTER TABLE users ADD CONSTRAINT unique_users_username  UNIQUE (username);