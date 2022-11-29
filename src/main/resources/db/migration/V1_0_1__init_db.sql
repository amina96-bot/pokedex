CREATE TABLE AUTH_USER_GROUP (
                                 AUTH_USER_GROUP_ID int8 PRIMARY KEY,
                                 USERNAME VARCHAR(255) UNIQUE NOT NULL,
                                 AUTH_GROUP VARCHAR(255) NOT NULL,
                                 CONSTRAINT USER_AUTH_USER_GROUP_FK FOREIGN KEY(USERNAME) REFERENCES pokedex.user(USERNAME),
                                 UNIQUE (USERNAME, AUTH_GROUP)
)
