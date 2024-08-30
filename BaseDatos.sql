
drop table movement 
drop table client  
drop table account  
drop table person  
-- Table Person
CREATE TABLE person (
    person_id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    gender CHAR(1) CHECK (gender IN ('M', 'F')) NOT NULL,
    birthdate DATE,
    document_number VARCHAR(20) UNIQUE NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20),
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Table Client (one-to-one relationship with Person)
CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    person_id INT UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_person
        FOREIGN KEY (person_id)
        REFERENCES person (person_id)
        ON DELETE CASCADE
);

CREATE TABLE account (
    account_id SERIAL PRIMARY KEY,
    client_id INT NOT NULL,
    account_number VARCHAR(50) UNIQUE NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    previous_balance DECIMAL(15, 2) NOT NULL,
    status VARCHAR(50) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_client
        FOREIGN KEY (client_id)
        REFERENCES client (client_id)
        ON DELETE CASCADE
);

CREATE TABLE movement (
    movement_id SERIAL PRIMARY KEY,
    account_id INT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    movement_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_account
        FOREIGN KEY (account_id)
        REFERENCES account (account_id)
        ON DELETE CASCADE
);


--INSERTAR PERSONAS
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(2, 'Jane Smith', 'F', '1995-10-29', '56435687', 'Av. Jorge Chavez 398', '956789654', true, '2024-08-28 02:40:43.679', '2024-08-28 02:40:43.679');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(3, 'Alice Johnson', 'F', '1995-12-16', '10019876', 'Av. Ramon Castilla 789', '901256734', true, '2024-08-28 02:40:43.679', '2024-08-28 02:40:43.679');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(1, 'Alvaro Vera Bendezu', 'M', '1994-04-03', '73899176', 'Av. Aviciaon 100', '925453345', true, '2024-08-28 02:40:43.679', '2024-08-28 04:29:29.000');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(4, 'Bob Brown', 'M', '1994-07-21', '56782345', 'Av. Parque 193', '954234789', false, '2024-08-28 02:40:43.679', '2024-08-28 04:32:15.469');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(7, 'Juana Perez', 'F', '1994-04-03', '73899188', 'Av. Aviciaon 100', '925453345', true, '2024-08-28 05:19:04.549', '2024-08-28 05:19:04.549');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(8, 'Jose Lema', 'M', '1994-04-03', '87564534', 'Otavalo sn y principal', '098254785', true, '2024-08-30 01:07:33.501', '2024-08-30 01:07:33.501');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(10, 'Marianela Montalvo', 'F', '1994-10-03', '45679765', 'Amazonas y NNUU', '097548965', true, '2024-08-30 01:09:14.198', '2024-08-30 01:09:14.198');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(11, 'Juan Osorio', 'M', '1994-09-03', '23657865', '13 Junio y Equinoccial', '098874587', true, '2024-08-30 01:10:22.146', '2024-08-30 01:10:22.146');
INSERT INTO public.person
(person_id, "name", gender, birthdate, document_number, address, phone, active, created_at, updated_at)
VALUES(12, 'Juan cASCOPO', 'M', '1994-09-03', '23656865', '13 Junio y Equinoccial', '011874587', true, '2024-08-30 01:10:22.146', '2024-08-30 01:10:22.146');


--INSERTAR CLIENTES
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(2, 2, 'password456', true, '2024-08-28 02:42:08.365', '2024-08-28 02:42:08.365');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(3, 3, 'password789', true, '2024-08-28 02:42:08.365', '2024-08-28 02:42:08.365');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(1, 1, '12345', true, '2024-08-28 02:42:08.365', '2024-08-28 03:58:00.929');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(4, 4, 'password101', false, '2024-08-28 02:42:08.365', '2024-08-28 04:32:15.469');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(6, 7, '1314123123', true, '2024-08-28 05:19:04.549', '2024-08-28 05:19:04.549');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(7, 8, '1234', true, '2024-08-30 01:07:33.438', '2024-08-30 01:07:33.441');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(8, 10, '5678', true, '2024-08-30 01:09:14.197', '2024-08-30 01:09:14.197');
INSERT INTO public.client
(client_id, person_id, "password", active, created_at, updated_at)
VALUES(9, 11, '1245', true, '2024-08-30 01:10:22.145', '2024-08-30 01:10:22.145');
