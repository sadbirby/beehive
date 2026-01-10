CREATE TABLE follows
(
    created_by         VARCHAR(255)                 NOT NULL,
    created_date       TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
    last_modified_by   VARCHAR(255)                 NOT NULL,
    last_modified_date TIMESTAMP WITHOUT TIME ZONE  NOT NULL,
    follower_id        VARCHAR(255)                 NOT NULL,
    followee_id        VARCHAR(255)                 NOT NULL,
    CONSTRAINT pk_follows PRIMARY KEY (follower_id, followee_id)
);

ALTER TABLE follows ADD CONSTRAINT FK_FOLLOWS_ON_FOLLOWEE FOREIGN KEY (followee_id) REFERENCES users (id);
ALTER TABLE follows ADD CONSTRAINT FK_FOLLOWS_ON_FOLLOWER FOREIGN KEY (follower_id) REFERENCES users (id);