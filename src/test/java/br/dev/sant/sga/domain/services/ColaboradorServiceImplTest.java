package br.dev.sant.sga.domain.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.github.javafaker.Faker;

import br.dev.sant.sga.domain.entities.Colaborador;
import br.dev.sant.sga.domain.entities.Colaborador.Situacao;
import br.dev.sant.sga.domain.entities.Equipamento;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorRequestDto;
import br.dev.sant.sga.infrastructure.dtos.ColaboradorResponseDto;
import br.dev.sant.sga.infrastructure.repositories.ColaboradorRepository;

@ExtendWith(MockitoExtension.class)
public class ColaboradorServiceImplTest {

	@Mock
	private ColaboradorRepository colaboradorRepository;

	@InjectMocks
	private ColaboradorServiceImpl colaboradorService;

	@Test
	void deveCriarColaboradorComSucesso() {

		var faker = new Faker();
		var dto = new ColaboradorRequestDto();

		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSetor(faker.commerce().department());

		when(colaboradorRepository.existsByName(dto.getNome())).thenReturn(false);

		when(colaboradorRepository.save(any(Colaborador.class))).thenAnswer(invocation -> invocation.getArgument(0));

		ColaboradorResponseDto response = colaboradorService.criarColaborador(dto);

		assertThat(response).isNotNull();
		assertThat(response.getNome()).isEqualTo(dto.getNome());
		assertThat(response.getEmail()).isEqualTo(dto.getEmail());
		assertThat(response.getSetor()).isEqualTo(dto.getSetor());
		assertThat(response.getSituacao()).isEqualTo("ATIVO");

		verify(colaboradorRepository, times(1)).save(any(Colaborador.class));

	}

	@Test
	void deveLancarErroAoTentarCriarColaboradorJaExistente() {

		var faker = new Faker();

		var dto = new ColaboradorRequestDto();

		dto.setNome(faker.name().fullName());

		when(colaboradorRepository.existsByName(dto.getNome())).thenReturn(true);

		var exception = assertThrows(IllegalArgumentException.class, () -> colaboradorService.criarColaborador(dto));

		assertThat(exception.getMessage()).isEqualTo("Colaborador ja cadastrado");

		verify(colaboradorRepository, never()).save(any(Colaborador.class));

	}

	@Test
	void deveAtualizarColaboradorComSucesso() {

		var faker = new Faker();
		Long colaboradorId = 1L;

		var dto = new ColaboradorRequestDto();
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSetor(faker.commerce().department());
		dto.setSituacao("ATIVO");

		var colaboradorExistente = new Colaborador();
		colaboradorExistente.setId(colaboradorId);
		colaboradorExistente.setNome("Renan Santana");
		colaboradorExistente.setEmail("renan@sant.dev.br");
		colaboradorExistente.setSetor("TI");
		colaboradorExistente.setSituacao(Situacao.ATIVO);

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.of(colaboradorExistente));

		when(colaboradorRepository.save(any(Colaborador.class))).thenAnswer(invocation -> invocation.getArgument(0));

		var response = colaboradorService.atualizarColaborador(dto, colaboradorId);

		assertThat(response).isNotNull();
		assertThat(response.getNome()).isEqualTo(dto.getNome());
		assertThat(response.getEmail()).isEqualTo(dto.getEmail());
		assertThat(response.getSetor()).isEqualTo(dto.getSetor());
		assertThat(response.getSituacao()).isEqualTo("ATIVO");

		verify(colaboradorRepository, times(1)).findById(colaboradorId);
		verify(colaboradorRepository, times(1)).save(any(Colaborador.class));

	}

	@Test
	void deveLancarErroAoTentarAtualizarColaboradorInexistente() {

		var faker = new Faker();
		Long colaboradorId = 99L;

		var dto = new ColaboradorRequestDto();
		dto.setNome(faker.name().fullName());
		dto.setEmail(faker.internet().emailAddress());
		dto.setSetor(faker.commerce().department());

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.empty());

		var exception = assertThrows(IllegalArgumentException.class,
				() -> colaboradorService.atualizarColaborador(dto, colaboradorId));

		assertThat(exception.getMessage()).isEqualTo("Colaborador nao encontrado");

		verify(colaboradorRepository, times(1)).findById(colaboradorId);
		verify(colaboradorRepository, never()).save(any(Colaborador.class));
	}

	@Test
	void deveDeletarColaboradorComSucesso() {

		Long colaboradorId = 99L;

		var colaboradorExistente = new Colaborador();
		colaboradorExistente.setId(colaboradorId);
		colaboradorExistente.setNome("Renan Santana");
		colaboradorExistente.setEmail("renan@sant.dev.br");
		colaboradorExistente.setSetor("TI");
		colaboradorExistente.setSituacao(Situacao.ATIVO);

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.of(colaboradorExistente));

		colaboradorService.deletarColaborador(colaboradorId);

		verify(colaboradorRepository, times(1)).findById(colaboradorId);
		verify(colaboradorRepository, times(1)).delete(colaboradorExistente);
	}

	@Test
	void deveLancarErroAoTentarDeletarColaboradorInexistente() {

		Long colaboradorId = 99L;

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.empty());

		var exception = assertThrows(IllegalArgumentException.class,
				() -> colaboradorService.deletarColaborador(colaboradorId));

		assertThat(exception.getMessage()).isEqualTo("Colaborador nao encontrado");

		verify(colaboradorRepository, never()).delete(any(Colaborador.class));

	}

	@Test
	void deveLancarErroAoTentarDeletarColaboradorComEquipamentoAssociado() {

		var faker = new Faker();

		Long colaboradorId = 99L;

		var colaborador = new Colaborador();
		colaborador.setNome(faker.name().fullName());
		colaborador.setEmail(faker.internet().emailAddress());
		colaborador.setSetor(faker.commerce().department());
		colaborador.setSituacao(Situacao.ATIVO);
		colaborador.setEquipamento(new Equipamento());

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.of(colaborador));

		var exception = assertThrows(IllegalArgumentException.class,
				() -> colaboradorService.deletarColaborador(colaboradorId));

		assertThat(exception.getMessage()).isEqualTo(
				"Não é possível deletar um colaborador que possui um equipamento associado. Por favor, desvincule o equipamento primeiro.");

		verify(colaboradorRepository, never()).delete(any(Colaborador.class));
	}

	@Test
	void deveRetornarColaboradorQuandoIdExistir() {

		var faker = new Faker();

		Long colaboradorId = 99L;

		var colaborador = new Colaborador();
		colaborador.setId(colaboradorId);
		colaborador.setNome(faker.name().fullName());
		colaborador.setEmail(faker.internet().emailAddress());
		colaborador.setSetor(faker.commerce().department());
		colaborador.setSituacao(Situacao.ATIVO);

		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.of(colaborador));

		var response = colaboradorService.buscarColaboradorPorId(colaboradorId);

		assertThat(response).isNotNull();
		assertThat(response.getId()).isEqualTo(colaboradorId);
		assertThat(response.getNome()).isEqualTo(colaborador.getNome());
		assertThat(response.getEmail()).isEqualTo(colaborador.getEmail());
		assertThat(response.getSetor()).isEqualTo(colaborador.getSetor());
		assertThat(response.getSituacao()).isEqualTo("ATIVO");

		verify(colaboradorRepository, times(1)).findById(colaboradorId);
	}

	@Test
	void deveLancarErroQuandoTentarRetornarColaboradorInexistente() {

		Long colaboradorId = 100L;
		
		when(colaboradorRepository.findById(colaboradorId)).thenReturn(java.util.Optional.empty());
		
		var exception = assertThrows(IllegalArgumentException.class, 
				() -> colaboradorService.buscarColaboradorPorId(colaboradorId));
		
		assertThat(exception.getMessage()).isEqualTo("Colaborador nao encontrado");
		
	    verify(colaboradorRepository, times(1)).findById(colaboradorId);
		
	}
}
