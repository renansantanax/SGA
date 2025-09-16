package br.dev.sant.sga.infrastructure.dtos;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EquipamentoRequestDto {
	
	@Size(max = 50, message = "O tipo do equipamento deve ter no máximo 50 caracteres.")
	@NotBlank(message = "O tipo do equipamento é obrigatório.")
	private String tipoEquipamento;
	
	@Size(max = 50, message = "O status do equipamento deve ter no máximo 50 caracteres.")
	@NotBlank(message = "O status do equipamento é obrigatório.")
	private String statusEquipamento;

	@Size(max = 100, message = "A marca deve ter no máximo 100 caracteres.")
	private String marca;
	
	@Size(max = 100, message = "O modelo deve ter no máximo 100 caracteres.")
	private String modelo;
	
	@Size(max = 100, message = "O service tag deve ter no máximo 100 caracteres.")
	private String serviceTag;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
	private LocalDate dataFimGarantia;
	
	private Long colaboradorId;
	
	@Size(max = 100, message = "O hostname deve ter no máximo 100 caracteres.")
	private String hostname;
	
	@Size(max = 45, message = "O IP deve ter no máximo 45 caracteres.")
	private String ip;
	
	@Size(max = 500, message = "As observações devem ter no máximo 500 caracteres.")
	private String observacoes;

}
