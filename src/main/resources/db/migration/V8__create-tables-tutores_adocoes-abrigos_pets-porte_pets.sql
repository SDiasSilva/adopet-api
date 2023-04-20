CREATE TABLE tutores_adocoes (
  tutor_id INT NOT NULL,
  adocoes_id INT NOT NULL,
  PRIMARY KEY (tutor_id, adocoes_id),
  FOREIGN KEY (tutor_id) REFERENCES tutores (id),
  FOREIGN KEY (adocoes_id) REFERENCES adocoes (id)
);

CREATE TABLE portes_pets (
  porte_id INT NOT NULL,
  pets_id INT NOT NULL,
  PRIMARY KEY (porte_id, pets_id),
  FOREIGN KEY (porte_id) REFERENCES portes (id),
  FOREIGN KEY (pets_id) REFERENCES pets (id)
);

CREATE TABLE abrigos_pets (
  abrigo_id INT NOT NULL,
  pets_id INT NOT NULL,
  PRIMARY KEY (abrigo_id, pets_id),
  FOREIGN KEY (abrigo_id) REFERENCES abrigos (id),
  FOREIGN KEY (pets_id) REFERENCES pets (id)
);
