package br.dev.sant.sga.domain.interfaces;

import java.util.List;

import br.dev.sant.sga.infrastructure.dtos.ColaboradorRequestDto;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorResponseDto;

public interface ColaboradorService {

	public List<ColaboradorResponseDto> listarColaboradores();
	
	public ColaboradorResponseDto criarColaborador(ColaboradorRequestDto request);
	
	public ColaboradorResponseDto atualizarColaborador(ColaboradorRequestDto request, Long id);
	
	public ColaboradorResponseDto  inativarColaborador(Long id);

	public ColaboradorResponseDto  deletarColaborador(Long id);
	
	public ColaboradorResponseDto buscarColaboradorPorId(Long id);
	
	
}
