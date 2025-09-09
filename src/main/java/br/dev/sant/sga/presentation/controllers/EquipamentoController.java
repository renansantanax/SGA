package br.dev.sant.sga.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.sant.sga.domain.interfaces.ColaboradorService;
import br.dev.sant.sga.domain.interfaces.EquipamentoService;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoRequestDto;
import br.dev.sant.sga.infrastructure.dtos.EquipamentoResponseDto;

@RestController
@RequestMapping("/equipamentos")
public class EquipamentoController {

	@Autowired
	EquipamentoService equipamentoService;
	@Autowired
	ColaboradorService colaboradorService;

	@PostMapping("criar")
	public ResponseEntity<EquipamentoResponseDto> create(EquipamentoRequestDto dto) {

		var response = equipamentoService.create(dto);

		return ResponseEntity.ok(response);

	}
	
	@PutMapping("atualizar/{id}")
	public ResponseEntity<EquipamentoResponseDto> update(Long id, EquipamentoRequestDto dto) {

		var response = equipamentoService.update(id, dto);

		return ResponseEntity.ok(response);

	}

	@DeleteMapping("deletar/{id}")
	public ResponseEntity<String> delete(Long id) {

		equipamentoService.delete(id);

		return ResponseEntity.ok("Equipamento deletado com sucesso");

	}

	
	@GetMapping("buscar/{id}")
	public ResponseEntity<EquipamentoResponseDto> getById(Long id) {

		var response = equipamentoService.getById(id);

		return ResponseEntity.ok(response);

	}

	@GetMapping("listar")
	public ResponseEntity<List<EquipamentoResponseDto>> getAll() {

		var response = equipamentoService.getAll();

		return ResponseEntity.ok(response);

	}

	@PutMapping("atribuir/{equipamentoId}/colaborador/{colaboradorId}")
	public ResponseEntity<EquipamentoResponseDto> assignToColaborador(Long equipamentoId, Long colaboradorId) {

		var response = equipamentoService.assignToColaborador(equipamentoId, colaboradorId);

		return ResponseEntity.ok(response);

	}

	@PutMapping("desatribuir/{equipamentoId}")
	public ResponseEntity<EquipamentoResponseDto> unassignFromColaborador(Long equipamentoId) {

		var response = equipamentoService.unassignFromColaborador(equipamentoId);

		return ResponseEntity.ok(response);

	}

	public ResponseEntity<EquipamentoResponseDto> getByServiceTag(String serviceTag) {

		var response = equipamentoService.getByServiceTag(serviceTag);

		return ResponseEntity.ok(response);

	}

}
