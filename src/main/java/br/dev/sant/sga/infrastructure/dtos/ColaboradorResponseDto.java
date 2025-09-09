package br.dev.sant.sga.infrastructure.dtos;

import lombok.Data;

@Data
public class ColaboradorResponseDto {

	private Long id;
	private String nome;
	private String email;
	private String setor;
	private String situacao;
	private EquipamentoResumidoDto equipamento;
	
}
