package br.dev.sant.sga.infrastructure.dtos;

import lombok.Data;

@Data
public class ColaboradorRequestDto {

	private String nome;
	private String email;
	private String setor;
	private String situacao;
	
}
