package br.dev.sant.sga.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.sant.sga.domain.entities.Colaborador;

public interface ColaboradorRepository extends JpaRepository<Colaborador, Long> {

	@Query("""
			SELECT COUNT(c) > 0 
			FROM Colaborador c WHERE c.nome = :nome
			""")
	boolean existsByName(@Param("nome") String nome);

	
	
}
