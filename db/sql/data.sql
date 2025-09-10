INSERT INTO tb_rol (name, description) VALUES
('ADMIN', 'Administrador'),
('USER', 'Usuario'),
('ASESOR', 'Asesor'),
('CLIENTE', 'Cliente');

INSERT INTO tb_user (name, last_name, birth_date, address, phone_number, email, base_salary, identity_document, password, rol_id, active) VALUES
('admin', 'admin', '1995-03-15', 'Av. Siempre Viva 123', '987654321', 'admin@example.com', 3500.50, 'DNI12345678', '$2a$10$nHB2BPj6d/OQquyihcJgkOLBiYWxm91aep49oWoGx9LM1zumoL4RG', 1, true),
('asesor', 'asesor', '1990-07-22', 'Jr. Las Flores 456', '912345678', 'asesor@example.com', 2800.00, 'DNI87654321', '$2a$10$XkhyQ7TPYp2v6mWI1f6zJ.0sIfpC2Lb4LiXIX3kFFcF1x57dJ9n9S', 2, true),
('cliente', 'cliente', '1985-11-05', 'Calle Central 789', '998877665', 'cliente@example.com', 4200.75, 'DNI11223344', '$2a$10$95kyjMjeT84/C6V2FE3mFOBrk7U0TV.aysozAvFaAytxqQPJRvge2', 3, true);

