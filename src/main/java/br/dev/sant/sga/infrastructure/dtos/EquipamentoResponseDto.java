package br.dev.sant.sga.infrastructure.dtos;

import lombok.Data;

@Data
public class EquipamentoResponseDto {
	private Long id;
	private String tipoEquipamento;
	private String statusEquipamento;
	private String marca;
	private String modelo;
	private String serviceTag;
	private String hostname;
	private String ip;
	private String dataFimGarantia; // Formato: "yyyy-MM-dd"
	private String observacoes;
	private ColaboradorResumidoDto colaborador;
}
