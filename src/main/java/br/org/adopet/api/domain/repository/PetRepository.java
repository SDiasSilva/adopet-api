package br.org.adopet.api.domain.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.domain.model.Pet;

public interface PetRepository extends JpaRepository<Pet, Long>{

}
