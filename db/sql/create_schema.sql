CREATE TABLE tb_rol (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(255)
);

CREATE TABLE tb_user (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100),
    birth_date DATE,
    address VARCHAR(255),
    phone_number VARCHAR(20),
    email VARCHAR(150) UNIQUE,
    base_salary NUMERIC(15,2),
    identity_document VARCHAR(50) UNIQUE,
    password VARCHAR(255) NOT NULL,
    rol_id BIGINT NOT NULL,
    active boolean not null,
    CONSTRAINT fk_user_rol FOREIGN KEY (rol_id) REFERENCES tb_rol(id)
);
