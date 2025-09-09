package br.dev.sant.sga.infrastructure.dtos;

public record EquipamentoResumidoDto(
	    Long id,
	    String tipoEquipamento,
	    String modelo,
	    String serviceTag
	) {
	}