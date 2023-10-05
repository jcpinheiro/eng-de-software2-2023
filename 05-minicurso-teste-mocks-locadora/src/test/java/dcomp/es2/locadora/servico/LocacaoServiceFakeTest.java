package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.LocacaoBuilder;
import dcomp.es2.locadora.builder.UsuarioBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.repositorio.LocacaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static dcomp.es2.locadora.builder.FilmeBuilder.umFilme;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class LocacaoServiceFakeTest {

    private LocacaoService locacaoService;
    private Usuario usuario;

    private LocacaoRepository locacaoRepository;

    @BeforeEach
    public void setup() {
        locacaoService = new LocacaoService();
        usuario = UsuarioBuilder.umUsuario().constroi();
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

        //Mockito.verify(locacaoRepository).salva(locacao);
        verify(locacaoRepository, times(1)).salva(locacao);


    }
}
