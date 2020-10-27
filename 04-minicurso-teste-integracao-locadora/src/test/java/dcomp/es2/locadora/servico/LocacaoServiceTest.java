package dcomp.es2.locadora.servico;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.jupiter.api.Assumptions;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.builder.LocacaoBuilder;
import dcomp.es2.locadora.builder.UsuarioBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.repositorio.LocacaoRepository;
import dcomp.es2.locadora.utils.DataUtils;

public class LocacaoServiceTest {
	
	private LocacaoService locacaoService;
	private Usuario usuario;
	
	private LocacaoRepository locacaoRepository;
	private SPCService spcService;
	private EmailService emailService;

	
	@Before
	public void setup() {
		locacaoService = new LocacaoService();
		usuario = UsuarioBuilder.umUsuario().constroi();
		
		locacaoRepository = Mockito.mock(LocacaoRepository.class);
		spcService = Mockito.mock(SPCService.class);
		emailService = Mockito.mock(EmailService.class );
		
		locacaoService.setLocacaoRepository(locacaoRepository );
		locacaoService.setSpcService(spcService );
		locacaoService.setEmailService(emailService );
	}

	
	@Test
	public void testaUmaLocacao() {
		
		Assume.assumeFalse( LocalDate.now().getDayOfWeek() ==  DayOfWeek.SATURDAY );

		// cenário
		Filme filme = FilmeBuilder.umFilme().constroi();
				
		// ação
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);

		// verificação

		Assert.assertThat(locacao.getValor(), is(equalTo(4.0)));
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataLocacao(), LocalDate.now() ), is(true) );
		Assert.assertThat(DataUtils.isMesmaData(locacao.getDataPrevista(), DataUtils.amanha()), is(true) );
		
		
		Mockito.verify(locacaoRepository, Mockito.times(1)).salva(locacao);
		
	}

	
	@Test(expected=RuntimeException.class)
	public void naoDeveAlugarFilmeSemEstoque() {
		
		Filme filme = FilmeBuilder
		                .umFilme()
		                .semEstoque()
		                .constroi();
		
		locacaoService.alugarFilme(usuario, filme);
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
		Assert.assertThat(locacao.getValor(), is(7.60d) );		
	}
	
	@Test
	public void deveAplicar30PctDeDescontoNoTerceiroFilme() {
		
		// cenário
		List<Filme> filmes = Arrays.asList( FilmeBuilder.umFilme().constroi(),
				                            FilmeBuilder.umFilme().constroi(),
				                            FilmeBuilder.umFilme().constroi() );                
		
		//ação
		Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);
		
		
		// verificação
		// 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 = 10.40d
		Assert.assertEquals(10.40d, locacao.getValor(), 0.00001);		
		
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
		Assert.assertEquals(12.40d, locacao.getValor(), 0.00001);		
		
	}
	
	
	@Test
	//@Ignore
	public void naoDeveDevolverUmFilmeNoDomingo() {

		assumingThat(LocalDate.now().getDayOfWeek().equals( .SATURDAY),
				() -> {
					// cenário
					Filme filme = new Filme("Batman o Retorno", 2, 5.0);

					// ação
					Locacao locacao = locacaoService.alugarFilme(usuario, filme);

					// verificação
					LocalDate dataRetorno = locacao.getDataPrevista();

					assertTrue(dataRetorno.getDayOfWeek().equals(DayOfWeek.MONDAY) );
				} );
	}
	
	
	@Test(expected=IllegalStateException.class)
	public void naoDeveAlugarFilmeParaUsuarioNegativado() {
		
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario Ok").constroi();
				
		Filme filme = FilmeBuilder.umFilme().constroi();
	
		Mockito.when(spcService.estaNegativado(usuario) ).thenReturn(true );
		
		Locacao locacao = locacaoService.alugarFilme(usuario, filme);
	}
	
	@Test
	public void deveEnviarEmailParaUsuariosAtrasados() {
		
		Usuario usuario1 = UsuarioBuilder.umUsuario().comNome("Usuario 1").constroi();
		Usuario usuario2 = UsuarioBuilder.umUsuario().comNome("Usuario 2").constroi();
		Usuario usuario3 = UsuarioBuilder.umUsuario().comNome("Usuario 3").constroi();
		Usuario usuario4 = UsuarioBuilder.umUsuario().comNome("Usuario 4").constroi();
		
		List<Locacao> locacoesEmAtraso = Arrays.asList( LocacaoBuilder.umaLocacao().paraUsuario(usuario1).emAtraso().constroi(),
				                                LocacaoBuilder.umaLocacao().paraUsuario(usuario2).emAtraso().constroi(),
				                                LocacaoBuilder.umaLocacao().paraUsuario(usuario3).emAtraso().constroi() );
		
		Mockito.when(locacaoRepository.emAtraso()).thenReturn(locacoesEmAtraso );
		
		locacaoService.notificaUsuariosEmAtraso();
		
		Mockito.verify(emailService, Mockito.times(1)).notifica(usuario1);
		Mockito.verify(emailService, Mockito.times(1)).notifica(usuario2);
		Mockito.verify(emailService, Mockito.times(1)).notifica(usuario3);
		
		Mockito.verify(emailService, Mockito.never() ).notifica(usuario4);
		
		Mockito.verifyNoMoreInteractions(emailService );
		
		
		
	}
		
		
		
	

}