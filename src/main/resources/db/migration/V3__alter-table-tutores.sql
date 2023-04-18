ALTER TABLE tutores
ADD COLUMN cidade_id INT;

ALTER TABLE tutores
ADD CONSTRAINT fk_cidade_id FOREIGN KEY (cidade_id) REFERENCES cidades (id);

ALTER TABLE tutores
DROP COLUMN cidade;
