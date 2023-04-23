package br.org.adopet.api.domain.repository;


import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import br.org.adopet.api.domain.model.Pet;
public interface PetRepository extends JpaRepository<Pet, Long>{

	List<Pet> findAllByAdotadoIsFalse();

	Optional<Pet> findByIdAndAdotadoIsFalse(Long id);

	Page<Pet> findAllByAdotadoIsFalse(Pageable paginacao);


}
