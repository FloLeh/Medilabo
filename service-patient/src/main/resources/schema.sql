DROP TABLE IF EXISTS patient;

CREATE TABLE patient (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         first_name VARCHAR(100) NOT NULL,
                         last_name VARCHAR(100) NOT NULL,
                         birthdate DATE NOT NULL,
                         gender CHAR(1) NOT NULL,
                         address VARCHAR(255),
                         phone VARCHAR(20)
);