package br.org.adopet.api.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import br.org.adopet.api.domain.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long>{

}
