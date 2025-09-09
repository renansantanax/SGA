package br.dev.sant.sga.presentation.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.dev.sant.sga.domain.interfaces.ColaboradorService;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorRequestDto;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorResponseDto;

@RestController
@RequestMapping("/colaboradores")
public class ColaboradorController {

	@Autowired
	ColaboradorService colaboradorService;

	@GetMapping("listar")
	public ResponseEntity<List<ColaboradorResponseDto>> listarColaboradores() {
		var response = colaboradorService.listarColaboradores();
		return ResponseEntity.ok(response);
	}

	@PostMapping("criar")
	public ResponseEntity<ColaboradorResponseDto> criarColaborador(ColaboradorRequestDto request) {
		var response = colaboradorService.criarColaborador(request);
		return ResponseEntity.ok(response);
	}

	@PutMapping("atualizar/{idColaborador}")
	public ResponseEntity<ColaboradorResponseDto> atualizarColaborador(@PathVariable Long idColaborador,
			ColaboradorRequestDto request) {

		var response = colaboradorService.atualizarColaborador(request, idColaborador);

		return ResponseEntity.ok(response);
	}

	@PutMapping("deletar/{id}")
	public ResponseEntity<ColaboradorResponseDto> deletarColaborador(@PathVariable Long id) {
		var response = colaboradorService.deletarColaborador(id);
		return ResponseEntity.ok(response);
	}

	@GetMapping("buscar/{id}")
	public ResponseEntity<ColaboradorResponseDto> buscarColaboradorPorId(@PathVariable Long id) {
		var response = colaboradorService.buscarColaboradorPorId(id);
		return ResponseEntity.ok(response);
	}

}
