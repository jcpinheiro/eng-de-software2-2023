package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.builder.UsuarioBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.repositorio.LocacaoRepository;
import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class LocacaoServiceTest {

    private LocacaoService locacaoService;
    private Usuario usuario;
    private LocacaoRepository locacaoRepository;
    private SPCService spcService;
    private EmailService emailService;


    @BeforeEach
    public void setup() {
        locacaoService = new LocacaoService();
        usuario = UsuarioBuilder.umUsuario().constroi();

        locacaoService.setLocacaoRepository(locacaoRepository);
        locacaoService.setSpcService(spcService);
        locacaoService.setEmailService(emailService);
    }


    //@Test
    public void testaUmaLocacao() {
        Assumptions.assumeFalse(LocalDate.now().getDayOfWeek() == DayOfWeek.SATURDAY);

        // cenário
        Filme filme = FilmeBuilder.umFilme().constroi();

        // ação
        Locacao locacao = locacaoService.alugarFilme(usuario, filme);

        // verificação
        assertThat(locacao.getValor(), is(equalTo(4.0)));
        assertThat(locacao.getDataLocacao().equals(LocalDate.now()), is(true));
        assertThat(locacao.getDataPrevista(), CoreMatchers.equalTo(LocalDate.now()));

        //	Mockito.verify(locacaoRepository, Mockito.times(1)).salva(locacao);

    }


    //@Test
    public void naoDeveAlugarFilmeSemEstoque() {

        Filme filme = FilmeBuilder
                .umFilme()
                .semEstoque()
                .constroi();
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> locacaoService.alugarFilme(usuario, filme),
                "Deveria ter lançado uma exceção de filme fora de estoque");

        assertTrue(exception.getMessage().contains("sem estoque."));
    }


    //@Test
    public void deveAplicarDesconto10PctNoSegundoFilme() {
        // cenário
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi());

        //ação
        Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

        // verificação
        // 4 + 4*90% = 4 + 3.60 = 7.60
        assertThat(locacao.getValor(), is(7.60d));
    }

    //@Test
    public void deveAplicar30PctDeDescontoNoTerceiroFilme() {

        // cenário
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi());

        //ação
        Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

        // verificação
        // 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 = 10.40d
        Assertions.assertEquals(10.40d, locacao.getValor(), 0.00001);

    }

    //@Test
    public void deveAplicarDesconto50PctNoQuartoFilme() {

        // cenário
        List<Filme> filmes = Arrays.asList(FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi(),
                FilmeBuilder.umFilme().constroi());

        //ação
        Locacao locacao = locacaoService.alugarFilmes(usuario, filmes);

        // verificação
        // 4 + 4*90% + 4 * 0.70 = 4 + 3.60 + 2.80 + 2.0 = 12.40
        Assertions.assertEquals(12.40d, locacao.getValor(), 0.00001);
    }


    //@Test
    //@Ignore
    public void naoDeveDevolverUmFilmeNoDomingo() {

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