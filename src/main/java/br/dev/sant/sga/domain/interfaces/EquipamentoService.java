package br.dev.sant.sga.domain.interfaces;

import java.util.List;

import br.dev.sant.sga.infrastructure.dtos.EquipamentoRequestDto;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoResponseDto;

public interface EquipamentoService {

	public EquipamentoResponseDto create(EquipamentoRequestDto dto);
	
	public EquipamentoResponseDto update(Long id, EquipamentoRequestDto dto);
	
	public EquipamentoResponseDto getById(Long id);
	
	public void delete(Long id);
	
	public EquipamentoResponseDto getByServiceTag(String serviceTag);
	
	public List<EquipamentoResponseDto> getAll();
	
	public EquipamentoResponseDto assignToColaborador(Long equipamentoId, Long colaboradorId);
	
	public EquipamentoResponseDto unassignFromColaborador(Long equipamentoId);
	
	
}
