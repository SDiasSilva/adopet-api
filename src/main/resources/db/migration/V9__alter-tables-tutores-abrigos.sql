ALTER TABLE tutores
ADD COLUMN usuario_id INT,
ADD CONSTRAINT fk_tutor_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
DROP COLUMN email,
DROP COLUMN senha;

ALTER TABLE abrigos
ADD COLUMN usuario_id INT,
ADD CONSTRAINT fk_abrigo_usuario_id FOREIGN KEY (usuario_id) REFERENCES usuarios (id),
DROP COLUMN email;