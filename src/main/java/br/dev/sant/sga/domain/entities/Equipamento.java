package br.dev.sant.sga.domain.entities;

import java.time.LocalDate;

import br.dev.sant.sga.domain.enums.StatusEquipamento;
import br.dev.sant.sga.domain.enums.TipoEquipamento;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "equipamentos")
public class Equipamento {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private TipoEquipamento tipoEquipamento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 50)
    private StatusEquipamento statusEquipamento;

    @Column(length = 100)
    private String marca;

    @Column(length = 100)
    private String modelo;

    @Column(unique = true, length = 100)
    private String serviceTag;

    private LocalDate dataFimGarantia;

    @OneToOne
    @JoinColumn(name = "colaborador_id", unique = true)
    private Colaborador colaborador;

    @Column(length = 100)
    private String hostname;

    @Column(length = 45) 
    private String ip;

    @Column(columnDefinition = "TEXT")
    private String observacoes;
	
}

