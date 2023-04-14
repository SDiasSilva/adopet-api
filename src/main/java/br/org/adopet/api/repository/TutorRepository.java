package br.org.adopet.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.model.Tutor;

public interface TutorRepository extends JpaRepository<Tutor, Long>{

}
