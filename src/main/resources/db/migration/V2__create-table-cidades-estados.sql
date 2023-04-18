CREATE TABLE estados (
  id INT AUTO_INCREMENT PRIMARY KEY,
  sigla VARCHAR(2),
  nome VARCHAR(50)
);

CREATE TABLE cidades (
  id INT AUTO_INCREMENT PRIMARY KEY,
  nome VARCHAR(100),
  estado_id INT,
  FOREIGN KEY (estado_id) REFERENCES estados (id)
);