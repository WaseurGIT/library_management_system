CREATE TABLE studentDetails
(
    id          INT PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255)                                            NOT NULL,
    email       VARCHAR(255)                                            NOT NULL UNIQUE KEY,
    dept        VARCHAR(10) NOT NULL,
    intake VARCHAR(10) NOT NULL,
    sec VARCHAR (10) NOT NULL ,
);