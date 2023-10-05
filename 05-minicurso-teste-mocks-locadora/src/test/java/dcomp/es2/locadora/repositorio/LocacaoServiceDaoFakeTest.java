package dcomp.es2.locadora.repositorio;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.servico.LocacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import static dcomp.es2.locadora.builder.FilmeBuilder.umFilme;
import static dcomp.es2.locadora.builder.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class LocacaoServiceDaoFakeTest {

	private LocacaoService locacaoService;
	private Usuario usuario;

	@BeforeEach
	public void setup(){
		usuario = umUsuario().constroi();

		locacaoService = new LocacaoService();
		LocacaoRepository dao = new LocacaoRepositoryFake();
		locacaoService.setLocacaoRepository(dao);
	}
	
	@Test
	public void deveAlugarFilme() throws Exception {
/*		// cenário
		Filme filme = umFilme().constroi();

		// ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		// verificação
		assertThat(locacao.getValor(), is(equalTo(4.0)));
		assertThat(locacao.getDataLocacao(), is(LocalDate.now()) );
		assertThat( locacao.getDataPrevista(), is(LocalDate.now().plusDays(1)) );*/
	}
	
	public void naoDeveAlugarFilmeSemEstoque() throws Exception{
		Filme filme = umFilme()
				.semEstoque()
				.constroi();

		final RuntimeException exception =
				assertThrows(RuntimeException.class,
						() -> locacaoService.alugarFilme(usuario, filme),
						"Deveria lançar a exceção RuntimeException");

		assertTrue(exception.getMessage().contains("sem estoque."));

	}
	
	@Test
	public void naoDeveAlugarFilmeSemUsuario() {
		//cenario
		List<Filme> filmes = List.of(FilmeBuilder.umFilme().constroi());

		final RuntimeException exception =
				assertThrows(RuntimeException.class,
						() -> locacaoService.alugarFilmes(null, filmes),
						"Deveria lançar a exceção RuntimeException");

		assertTrue(exception.getMessage().contains("Usuário não pode ser nulo"));
	}


	@Test
	public void deveDevolverNaSegundaAoAlugarNoSabado() {
		assumingThat(LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY),
				() -> {
					// cenário
					Filme filme = new Filme("Batman o Retorno", 2, 5.0);

					// ação
					Locacao locacao = locacaoService.alugarFilme(usuario, filme);

					// verificação
					LocalDate dataRetorno = locacao.getDataPrevista();

					assertTrue(dataRetorno.getDayOfWeek().equals(DayOfWeek.MONDAY));
				});

	}

}