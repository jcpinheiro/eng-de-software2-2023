package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LocacaoServiceTest_V2 {
	
	
	private LocacaoService locacaoService;
	private Usuario usuario;

	
	@BeforeEach
	public void setup() {
		locacaoService = new LocacaoService();
		usuario = new Usuario("Fulano");
	}

	@Test
	public void testaUmaLocacao() {
		
		// cenário
		Filme filme = FilmeBuilder.umFilme().constroi();
				
		// ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		// verificação

		assertThat(locacao.getValor(), is(equalTo(4.0)) );
		assertThat(locacao.getDataLocacao().equals(LocalDate.now() ), is(true) );
		assertThat(locacao.getDataRetorno().equals(LocalDate.now().plusDays(1)), is(true) );

		//Assert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.amanha()), is(true) );
		//MatcherAssert.assertThat(DataUtils.isMesmaData(locacao.getDataRetorno(), DataUtils.amanha()), is(true) );
	}


	@Test
	public void naoDeveAlugarFilmeSemEstoque() {

		Filme filme = FilmeBuilder
		                .umFilme()
		                .semEstoque()
		                .constroi();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> locacaoService.alugarFilme(usuario, filme),
				"Deveria ter lançado um IllegalArgumentException");

		assertTrue(exception.getMessage().contains("Filme sem Estoque"));


	}


	@Test
	public void deveAplicarDesconto10PctNoSegundoFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( FilmeBuilder.umFilme().constroi(),
				                            FilmeBuilder.umFilme().constroi());                
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		
		// verificação
		// 4 + 4*90% = 4 + 3.60 = 7.60
		assertThat(locacao.getValor(), is(7.60d) );
		
	}
	
	@Test
	public void deveAplicarDesconto30PctNoTerceiroFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( FilmeBuilder.umFilme().constroi(),
				                            FilmeBuilder.umFilme().constroi(),
				                            FilmeBuilder.umFilme().constroi() );                
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		
		// verificação
		// 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 = 10.40d
		
		Assertions.assertNotNull(locacao );
		Assertions.assertEquals(10.40d, locacao.getValor(), 0.00001);
		
	}
	
	@Test
	public void deveAplicarDesconto50PctNoQuartoFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( FilmeBuilder.umFilme().constroi(),
                                            FilmeBuilder.umFilme().constroi(),
                                            FilmeBuilder.umFilme().constroi(),                
											FilmeBuilder.umFilme().constroi() );                
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		
		// verificação
		// 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 + 2.0 = 12.40
		
		Assertions.assertNotNull(locacao);
		Assertions.assertEquals(12.40d, locacao.getValor(), 0.00001);
		
	}
	
}