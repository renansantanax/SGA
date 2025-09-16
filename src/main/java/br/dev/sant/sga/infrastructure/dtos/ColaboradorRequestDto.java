package br.dev.sant.sga.infrastructure.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ColaboradorRequestDto {

	@Size(min = 3, max = 100, message = "Por favor, verifique o tamanho do nome (min: 3, max: 100).")
	@NotBlank(message = "Por favor, informe o nome do colaborador.")
	private String nome;

	@Size(min = 5, max = 120, message = "Por favor, verifique o tamanho do email (min: 5, max: 120).")
	@NotBlank(message = "Por favor, informe o email do colaborador.")
	@Email(regexp = ".+@.+\\..+", message = "Por favor, informe um email válido.")
	private String email;
	
	@Size(max = 60, message = "Por favor, verifique o tamanho do setor (max: 60).")
	private String setor;
	
	@Size(min = 3, max = 20, message = "Por favor, verifique o tamanho da situação (min: 3, max: 20).")
	@NotNull(message = "Por favor, informe a situação do colaborador.")
	private String situacao;
	
}
