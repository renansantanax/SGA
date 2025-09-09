package br.dev.sant.sga.infrastructure.dtos;

import lombok.Data;

@Data
public class EquipamentoRequestDto {
	
	private String tipoEquipamento;
	private String statusEquipamento;
	private String marca;
	private String modelo;
	private String serviceTag;
	private String dataFimGarantia; // Formato: "yyyy-MM-dd"
	private Long colaboradorId;
	private String hostname;
	private String ip;
	private String observacoes;

}
