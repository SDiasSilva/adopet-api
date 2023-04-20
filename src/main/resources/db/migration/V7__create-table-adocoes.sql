CREATE TABLE adocoes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pet_id INT NOT NULL,
    tutor_id INT NOT NULL,
    data DATE NOT NULL,
    FOREIGN KEY (pet_id) REFERENCES pets(id),
    FOREIGN KEY (tutor_id) REFERENCES tutores(id)
);
