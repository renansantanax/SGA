package br.dev.sant.sga.infrastructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.dev.sant.sga.domain.entities.Equipamento;

public interface EquipamentoRepository extends JpaRepository<Equipamento, Long> {

	@Query("SELECT e FROM Equipamento e WHERE e.serviceTag = :serviceTag" )
	Equipamento findByServiceTag(@Param("serviceTag") String serviceTag);
	
	@Query("SELECT e FROM Equipamento e WHERE e.colaborador.id = :colaboradorId")
	Equipamento findByColaboradorId(@Param("colaboradorId") Long colaboradorId);
	
}
