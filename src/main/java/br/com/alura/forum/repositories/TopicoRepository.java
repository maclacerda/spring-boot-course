package br.com.alura.forum.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.alura.forum.modelo.Topico;

public interface TopicoRepository extends JpaRepository<Topico, Long> {
	
	// Para explicitar um relacionamento podemos usar `findByCurso_Nome`
	Page<Topico> findByCursoNome(String nomeCurso, Pageable paginacao);
	
	// Exemplo de outra forma de definir metodos 
	@Query("SELECT t FROM Topico t WHERE t.curso.nome = :nomeCurso")
	List<Topico> findByCourseName(@Param("nomeCurso") String name);
	
}
