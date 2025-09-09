package br.dev.sant.sga.domain.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.dev.sant.sga.domain.entities.Equipamento;
import br.dev.sant.sga.domain.enums.StatusEquipamento;
import br.dev.sant.sga.domain.enums.TipoEquipamento;
import br.dev.sant.sga.domain.interfaces.EquipamentoService;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorResumidoDto;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoRequestDto;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoResponseDto;
import br.dev.sant.sga.infrastructure.repositories.ColaboradorRepository;
import br.dev.sant.sga.infrastructure.repositories.EquipamentoRepository;

@Service
@Transactional
public class EquipamentoServiceImpl implements EquipamentoService {

	@Autowired
	EquipamentoRepository equipamentoRepository;
	
	@Autowired 
	ColaboradorRepository colaboradorRepository;

	@Override
	public EquipamentoResponseDto create(EquipamentoRequestDto dto) {

		var equipamento = new Equipamento();

		equipamento.setTipoEquipamento(TipoEquipamento.valueOf(dto.getTipoEquipamento().toUpperCase()));
		equipamento.setStatusEquipamento(StatusEquipamento.fromString(dto.getStatusEquipamento()));
		equipamento.setMarca(dto.getMarca());
		equipamento.setModelo(dto.getModelo());
		equipamento.setServiceTag(dto.getServiceTag());
		equipamento.setHostname(dto.getHostname());
		equipamento.setIp(dto.getIp());
		equipamento.setObservacoes(dto.getObservacoes());

		equipamentoRepository.save(equipamento);

		return mapToDto(equipamento);
	}

	@Override
	public EquipamentoResponseDto update(Long id, EquipamentoRequestDto dto) {

		var equipamento = equipamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

		equipamento.setTipoEquipamento(TipoEquipamento.valueOf(dto.getTipoEquipamento().toUpperCase()));
		equipamento.setStatusEquipamento(StatusEquipamento.fromString(dto.getStatusEquipamento()));
		equipamento.setMarca(dto.getMarca());
		equipamento.setModelo(dto.getModelo());
		equipamento.setServiceTag(dto.getServiceTag());
		equipamento.setHostname(dto.getHostname());
		equipamento.setIp(dto.getIp());
		equipamento.setObservacoes(dto.getObservacoes());

		equipamentoRepository.save(equipamento);

		return mapToDto(equipamento);
	}

	@Override
	public EquipamentoResponseDto getById(Long id) {
		var equipamento = equipamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));

		return mapToDto(equipamento);
	}

	@Override
	public void delete(Long id) {
		
		var equipamento = equipamentoRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Equipamento nao encontrado"));
		
		equipamentoRepository.delete(equipamento);
	}

	@Override
	public EquipamentoResponseDto getByServiceTag(String serviceTag) {

		var equipamento = equipamentoRepository.findByServiceTag(serviceTag);

		if (equipamento == null) {
			throw new RuntimeException("Equipamento nao encontrado");
		}
		
		return mapToDto(equipamento);

	}

	@Override
	public List<EquipamentoResponseDto> getAll() {
		
		var equipamentos = equipamentoRepository.findAll();
		return equipamentos.stream().map((equipamento) -> mapToDto(equipamento)).collect(Collectors.toList());
		
	}

	@Override
	public EquipamentoResponseDto assignToColaborador(Long equipamentoId, Long colaboradorId) {
		
	    var equipamento = equipamentoRepository.findById(equipamentoId)
	            .orElseThrow(() -> new RuntimeException("Equipamento com ID " + equipamentoId + " não encontrado."));

	    var colaborador = colaboradorRepository.findById(colaboradorId)
	            .orElseThrow(() -> new RuntimeException("Colaborador com ID " + colaboradorId + " não encontrado."));

	           
	    if (equipamento.getColaborador() != null) {
	        throw new RuntimeException("Operação falhou: O equipamento " + equipamento.getModelo() + " já está atribuído a outro colaborador.");
	    }
	    
	    if (colaborador.getEquipamento() != null) {
	        throw new RuntimeException("Operação falhou: O colaborador " + colaborador.getNome() + " já possui um equipamento atribuído.");
	    }
	    
	    equipamento.setColaborador(colaborador);
	    colaborador.setEquipamento(equipamento);
	    equipamento.setStatusEquipamento(StatusEquipamento.EM_USO);
	    
	    equipamentoRepository.save(equipamento);
	    colaboradorRepository.save(colaborador);
	    
	    return mapToDto(equipamento);
	}

	@Override
	public EquipamentoResponseDto unassignFromColaborador(Long equipamentoId) {
		
		var equipamento = equipamentoRepository.findById(equipamentoId)
				.orElseThrow(() -> new RuntimeException("Equipamento com ID " + equipamentoId + " não encontrado."));
		
		if (equipamento.getColaborador() != null) {
			var colaborador = equipamento.getColaborador();
			equipamento.setColaborador(null);
			colaborador.setEquipamento(null);
			equipamento.setStatusEquipamento(StatusEquipamento.DISPONIVEL);
			equipamentoRepository.save(equipamento);
			colaboradorRepository.save(colaborador);
		}
		else {
			throw new RuntimeException("Operação falhou: O equipamento " + equipamento.getModelo()
					+ " não está atribuído a nenhum colaborador.");
		}

		return mapToDto(equipamento);
	}

	private EquipamentoResponseDto mapToDto(Equipamento equipamento) {
		var response = new EquipamentoResponseDto();

		response.setId(equipamento.getId());
		response.setTipoEquipamento(equipamento.getTipoEquipamento().name());
		response.setStatusEquipamento(equipamento.getStatusEquipamento().name());
		response.setMarca(equipamento.getMarca());
		response.setModelo(equipamento.getModelo());
		response.setServiceTag(equipamento.getServiceTag());
		response.setHostname(equipamento.getHostname());
		response.setIp(equipamento.getIp());

		  if (equipamento.getColaborador() != null) {
		        var colaboradorEntity = equipamento.getColaborador();
		        
		        var colaboradorDto = new ColaboradorResumidoDto(
		            colaboradorEntity.getId(),
		            colaboradorEntity.getNome(),
		            colaboradorEntity.getEmail()
		        );
		        response.setColaborador(colaboradorDto);
		    } else {
		        response.setColaborador(null);
		    }
		
		return response;
	}

}
