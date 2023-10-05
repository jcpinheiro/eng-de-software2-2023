package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class LocacaoServiceTest_V2 {

	private LocacaoService locacaoService;
	private Usuario usuario;

	@BeforeEach
	public void setup() {
		locacaoService = new LocacaoService();
		usuario = new Usuario("Fulano");
	}

	@Test
	public void testaUmaLocacaoValida() {
		// cenário
		Filme filme = FilmeBuilder.umFilme().constroi();
		LocalDate hoje = LocalDate.now();
		LocalDate amanha = LocalDate.now().plusDays(1);
		// ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filme);

		// verificação
		assertThat(locacao.getValor(), is(equalTo(4.0)) );
		assertThat(locacao.getDataLocacao().equals(hoje ), is(true) );
		assertThat(locacao.getDataRetorno().equals(amanha), is(true) );

	}


	@Test
	public void naoDeveAlugarFilmeSemEstoque() {

		Filme filme = FilmeBuilder
		                .umFilme()
		                .semEstoque()
		                .constroi();

		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class,
				() -> locacaoService.alugarFilmes(usuario, filme),
				"Deveria ter lançado um IllegalArgumentException");

		assertTrue(exception.getMessage().contains("Filme sem Estoque"));
	}

	@Test
	public void deveAplicarDesconto10PctNoSegundoFilme() {
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario,
				               FilmeBuilder.umFilme().constroi(),
				               FilmeBuilder.umFilme().constroi());
		// verificação
		// 4 +  4 * 90% = 4 + 3.60 = 7.60
		assertThat(locacao.getValor(), is(7.60d) );
		
	}
	
	@Test
	public void deveAplicarDesconto30PctNoTerceiroFilme() {

		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario,
				            FilmeBuilder.umFilme().constroi(),
				            FilmeBuilder.umFilme().constroi(),
				            FilmeBuilder.umFilme().constroi() );
		// verificação
		// 4 + 4*90% + 4 * 70% = 4 + 3.60 + 2.80 = 10.40d
		
		Assertions.assertNotNull(locacao );
		Assertions.assertEquals(10.40d, locacao.getValor(), 0.00001);
		
	}
	
	@Test
	//@Disabled
	public void deveAplicarDesconto50PctNoQuartoFilme() {
		
		// cenário
		List<Filme> filmes = List.of( FilmeBuilder.umFilme().constroi(),
                                            FilmeBuilder.umFilme().constroi(),
                                            FilmeBuilder.umFilme().constroi(),                
											FilmeBuilder.umFilme().constroi() );                
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario,
											FilmeBuilder.umFilme().constroi(),
											FilmeBuilder.umFilme().constroi(),
											FilmeBuilder.umFilme().constroi(),
											FilmeBuilder.umFilme().constroi() );

		// verificação
		// 4 + 4*90% + 4 * 70%  + 4*50% = 4 + 3.60 + 2.80 + 2.0 = 12.40
		Assertions.assertNotNull(locacao);
		Assertions.assertEquals(12.40d, locacao.getValor(), 0.00001);
		
	}

	@Test
	public void naoDeveDevolverUmFilmeNoDomingo() {

		Assumptions.assumingThat(LocalDate
						.now()
						.getDayOfWeek()
						.equals(DayOfWeek.SATURDAY),
				() -> {
					// cenário
					Filme filme = new Filme("Batman o Retorno", 2, 5.0);

					// ação
					Locacao locacao = locacaoService.alugarFilmes(usuario, filme);

					// verificação
					LocalDate dataRetorno = locacao.getDataLocacao();

					assertTrue(dataRetorno.getDayOfWeek().equals(DayOfWeek.MONDAY) );
				} );
	}
	
}