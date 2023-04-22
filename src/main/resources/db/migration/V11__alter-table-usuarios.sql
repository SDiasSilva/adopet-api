ALTER TABLE usuarios
ADD COLUMN funcao_id INT,
ADD CONSTRAINT fk_funcao_usuario_id FOREIGN KEY (funcao_id) REFERENCES funcoes (id);