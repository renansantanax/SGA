package br.dev.sant.sga.domain.enums;

import java.util.Arrays;

public enum StatusEquipamento {
	EM_USO, DISPONIVEL, MANUTENCAO, ESTOQUE, DESCARTADO;

	 public static StatusEquipamento fromString(String texto) {
        if (texto == null) {
            return null;
        }
        
        // Remove espaços e substitui por underline, além de deixar em maiúsculas
        String textoFormatado = texto.trim().replace(" ", "_").toUpperCase();

        for (StatusEquipamento status : StatusEquipamento.values()) {
            if (status.name().equals(textoFormatado)) {
                return status;
            }
        }
        
        // Se não encontrar, lança uma exceção com uma mensagem CLARA para o usuário!
        throw new IllegalArgumentException("Status de equipamento inválido: '" + texto + "'. Valores aceitos são: " + Arrays.toString(StatusEquipamento.values()));
    }
}
