package br.dev.sant.sga.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.dev.sant.sga.domain.entities.Colaborador;
import br.dev.sant.sga.domain.interfaces.ColaboradorService;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorRequestDto;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorResponseDto;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoResumidoDto;
import br.dev.sant.sga.infrastructure.repositories.ColaboradorRepository;

@Service
public class ColaboradorServiceImpl implements ColaboradorService {

	@Autowired
	ColaboradorRepository colaboradorRepository;

	@Override
	public List<ColaboradorResponseDto> listarColaboradores() {

		var colaboradores = colaboradorRepository.findAll();

		return colaboradores.stream().map((colaborador) -> mapToDto(colaborador)).collect(Collectors.toList());
	}

	@Override
	public ColaboradorResponseDto criarColaborador(ColaboradorRequestDto request) {

		if (colaboradorRepository.existsByName(request.getNome())) {
			throw new RuntimeException("Colaborador ja cadastrado");
		}

		var colaborador = new Colaborador();

		colaborador.setNome(request.getNome());
		colaborador.setEmail(request.getEmail());
		colaborador.setSetor(request.getSetor());
		colaborador.setSituacao(Colaborador.Situacao.ATIVO);
		colaborador.setEquipamento(null);

		colaboradorRepository.save(colaborador);

		return mapToDto(colaborador);
	}

	@Override
	public ColaboradorResponseDto atualizarColaborador(ColaboradorRequestDto request, Long id) {

		var colaborador = colaboradorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Colaborador nao encontrado"));

		colaborador.setNome(request.getNome());
		colaborador.setEmail(request.getEmail());
		colaborador.setSetor(request.getSetor());
		colaborador.setSituacao(Colaborador.Situacao.valueOf(request.getSituacao()));

		colaboradorRepository.save(colaborador);

		return mapToDto(colaborador);

	}

	@Override
	public ColaboradorResponseDto inativarColaborador(Long id) {

		var colaborador = colaboradorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Colaborador nao encontrado"));

		colaborador.setSituacao(Colaborador.Situacao.INATIVO);

		colaboradorRepository.save(colaborador);

		return mapToDto(colaborador);
	}

	@Override
	public ColaboradorResponseDto deletarColaborador(Long id) {

		var colaborador = colaboradorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Colaborador nao encontrado"));

	    if (colaborador.getEquipamento() != null) {
	        throw new RuntimeException("Não é possível deletar um colaborador que possui um equipamento associado. Por favor, desvincule o equipamento primeiro.");
	    }
		
		colaboradorRepository.delete(colaborador);

		return mapToDto(colaborador);
	}

	@Override
	public ColaboradorResponseDto buscarColaboradorPorId(Long id) {

		var colaborador = colaboradorRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Colaborador nao encontrado"));

		return mapToDto(colaborador);
	}

	private ColaboradorResponseDto mapToDto(Colaborador colaborador) {
		var response = new ColaboradorResponseDto();

		response.setId(colaborador.getId());
		response.setNome(colaborador.getNome());
		response.setEmail(colaborador.getEmail());
		response.setSetor(colaborador.getSetor());
		response.setSituacao(colaborador.getSituacao().name());

		if (colaborador.getEquipamento() != null) {
	        var equipamentoEntity = colaborador.getEquipamento();
	        
	        var equipamentoDto = new EquipamentoResumidoDto(
	            equipamentoEntity.getId(),
	            equipamentoEntity.getTipoEquipamento().name(),
	            equipamentoEntity.getModelo(),
	            equipamentoEntity.getServiceTag()
	        );
	        response.setEquipamento(equipamentoDto);
	    } else {
	        response.setEquipamento(null);
	    }

		return response;
	}

}
