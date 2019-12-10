package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.builder.LocacaoBuilder;
import dcomp.es2.locadora.builder.UsuarioBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.repositorio.LocacaoRepository;
import dcomp.es2.locadora.utils.DataUtils;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static dcomp.es2.locadora.builder.FilmeBuilder.umFilme;
import static dcomp.es2.locadora.builder.UsuarioBuilder.umUsuario;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.*;

public class LocacaoServiceTestJUnit5 {
	
	private LocacaoService locacaoService;
	private Usuario usuario;
	
	private LocacaoRepository locacaoRepository;
	private SPCService spcService;
	private EmailService emailService;

	
	@BeforeEach
	public void setup() {
		locacaoService = new LocacaoService();
		usuario = umUsuario().constroi();
		
		locacaoRepository = mock(LocacaoRepository.class);
		spcService = mock(SPCService.class);
		emailService = mock(EmailService.class );

		// Injeção
	/*	locacaoService.setLocacaoRepository(locacaoRepository );

		locacaoService.setSpcService(spcService );

		locacaoService.setEmailService(emailService );*/
	}

	@Test
	public void testaUmaLocacao() {
		
		// cenário
		Filme filme = umFilme().constroi();
				
		// ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		// verificação

		assertThat(locacao.getValor(), is(equalTo(4.0)));
		assertThat(locacao.getDataLocacao(), is(LocalDate.now()) );
		assertThat( locacao.getDataPrevista(), is(LocalDate.now().plusDays(1)) );
		
		verify(locacaoRepository, times(1)).salva(locacao);

	}

	
	@Test
	public void naoDeveAlugarFilmeSemEstoque() {

		Filme filme = umFilme()
				.semEstoque()
				.constroi();

		final RuntimeException exception =
				Assertions.assertThrows(RuntimeException.class,
				                        () -> locacaoService.alugarFilme(usuario, filme),
				                        "Deveria lançar a exceção RuntimeException");

		Assertions.assertTrue(exception.getMessage().contains("sem estoque."));

		
		;
	}

	
	@Test
	public void deveAplicarDesconto10PctNoSegundoFilme() {
		// cenário
		List<Filme> filmes = Arrays.asList( umFilme().constroi(),
				                            umFilme().constroi());
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		// verificação
		// 4 + 4*90% = 4 + 3.60 = 7.60
		assertThat(locacao.getValor(), is(7.60d) );
	}
	
	@Test
	public void deveAplicar30PctDeDescontoNoTerceiroFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( umFilme().constroi(),
				                            umFilme().constroi(),
				                            umFilme().constroi() );
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

		// verificação
		// 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 = 10.40d
		Assertions.assertEquals(10.40d, locacao.getValor(), 0.00001);
		
	}
	
	@Test
	public void deveAplicarDesconto50PctNoQuartoFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( umFilme().constroi(),
                                            umFilme().constroi(),
                                            umFilme().constroi(),
											umFilme().constroi() );
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		
		// verificação
		// 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 + 2.0 = 12.40
		Assertions.assertEquals(12.40d, locacao.getValor(), 0.00001);
		
	}
	
	

	@Test
	public void naoDeveAlugarFilmeParaUsuarioNegativado() {
		
		Usuario usuario2 = umUsuario().comNome("Usuario Ok").constroi();
				
		Filme filme = umFilme().constroi();
	
		when(spcService.estaNegativado(this.usuario)).thenReturn(true );


		IllegalStateException exception = Assertions.assertThrows(IllegalStateException.class,
				() -> locacaoService.alugarFilme(usuario, filme),
				"Deveria lançar um IllegalStateException");

		Assertions.assertTrue(exception.getMessage().contains("pendências no SPC"));

		verify(spcService).estaNegativado(usuario);
	}



	@Test
	public void deveEnviarEmailParaUsuariosAtrasados() {
		
		Usuario usuario1 = umUsuario().comNome("Usuario 1").constroi();
		Usuario usuario2 = umUsuario().comNome("Usuario 2").constroi();
		Usuario usuario3 = umUsuario().comNome("Usuario 3").constroi();
		Usuario usuario4 = umUsuario().comNome("Usuario 4").constroi();
		
		List<Locacao> locacoesEmAtraso = Arrays.asList(
				LocacaoBuilder.umaLocacao().paraUsuario(usuario1).emAtraso().constroi(),
				LocacaoBuilder.umaLocacao().paraUsuario(usuario2).emAtraso().constroi(),
				LocacaoBuilder.umaLocacao().paraUsuario(usuario3).emAtraso().constroi() );
		
		when(locacaoRepository.emAtraso()).thenReturn(locacoesEmAtraso );
		
		locacaoService.notificaUsuariosEmAtraso();
		
		verify(emailService, times(1)).notifica(usuario1);
		verify(emailService, times(1)).notifica(usuario2);
		verify(emailService, times(1)).notifica(usuario3);
		
		verify(emailService, never() ).notifica(usuario4);

		verifyNoMoreInteractions(emailService );

	}


	@Test
	public void deveTratarErroNoSPC() {
		List<Filme> filmes = Arrays.asList(umFilme().constroi());

		when(spcService.estaNegativado(this.usuario))
				.thenThrow(new RuntimeException("SPC fora do ar") );

		final RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
				() -> locacaoService.alugarFilmes(usuario, filmes),
				"Deveria lançar um RuntimeException");


	}
		
	

}