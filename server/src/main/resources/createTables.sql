/*
Создание базовых таблиц пользователей
*/

CREATE TABLE BALANCE
(
    id        BIGINT     NOT NULL AUTO_INCREMENT PRIMARY KEY,
    client_id BIGINT     NOT NULL,
    amount    DECFLOAT DEFAULT 0,
    name      VARCHAR(5) NOT NULL,
    FOREIGN KEY (client_id) REFERENCES CLIENT (id)
);

CREATE TABLE CLIENT
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login    VARCHAR(25) NOT NULL,
    password VARCHAR(25) NOT NULL,
    pin      SMALLINT    NOT NULL
);

/* Insert scope*/
INSERT INTO CLIENT (login, password, pin)
VALUES ('artur', 'qwerty', 3322);

INSERT INTO BALANCE(client_id, amount, name)
VALUES (2, 500, 'USD');

/* Insert scope*/

/* Select scope*/
SELECT *
FROM TESTCLIENT;

SELECT *
FROM CLIENT;

SELECT *
FROM BALANCE;

SELECT (SELECT id, login, password, pin FROM CLIENT WHERE client_id = id) AS info
FROM BALANCE;

SELECT id, client_id, amount, name
FROM BALANCE
WHERE id = 1
  AND (SELECT CLIENT.id, pin FROM CLIENT WHERE pin = 3322 AND CLIENT.id = id); // not work

SELECT BALANCE.id, client_id, amount, name, login, password, pin
FROM BALANCE
         INNER JOIN CLIENT ON CLIENT.id = BALANCE.client_id AND CLIENT.id = 1 AND CLIENT.pin = 1234;

SELECT BALANCE.id, client_id, amount, name, login, password, pin
FROM BALANCE
         INNER JOIN CLIENT
                    ON CLIENT.id = BALANCE.client_id
                        AND CLIENT.login = 'artur'
                        AND CLIENT.password = 'qwerty';
/* Select scope*/

/* Delete scope*/
DELETE
FROM BALANCE
WHERE id = 4
/* Delete scope*/