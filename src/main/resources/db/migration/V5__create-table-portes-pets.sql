CREATE TABLE portes (
  id INT AUTO_INCREMENT PRIMARY KEY,
  descricao VARCHAR(100) NOT NULL
);

CREATE TABLE pets (
  id INT AUTO_INCREMENT PRIMARY KEY,
  abrigo_id INT NOT NULL,
  nome VARCHAR(100) NOT NULL,
  porte_id INT NOT NULL,
  descricao TEXT,
  adotado BOOLEAN NOT NULL DEFAULT FALSE,
  data_nascimento DATE,
  foto VARCHAR(255),
  FOREIGN KEY (abrigo_id) REFERENCES abrigos (id),
  FOREIGN KEY (porte_id) REFERENCES portes (id)
);




