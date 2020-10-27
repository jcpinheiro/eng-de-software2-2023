
package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.builder.FilmeBuilder;
import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LocacaoServiceTest {
    private LocacaoService locacaoService;
    private Usuario joao;


    @BeforeEach
    public void inicio() {
        locacaoService = new LocacaoService();
         joao = new Usuario("Joao");

    }

    @Test
    public void deveAlugarFilme() {

        // cenário

        Filme filme = new Filme("Batman o Retorno", 3, 4.0);

        // ação
        Locacao locacao = locacaoService.alugarFilmes(joao, filme);

        // verificação
        assertEquals(locacao.getValor(),4.0, 0.00001 );
        assertEquals(locacao.getDataLocacao(), LocalDate.now() );
        assertTrue(locacao.getDataRetorno().equals(LocalDate.now().plusDays(1)) );

    }

    @Test
    public void naoDeveAlugarFilmeSemEstoque() {

        // cenário

        Filme filme = FilmeBuilder.umFilme()
                            .semEstoque()
                            .comPrecoDe(6.50 )
                            .constroi();

        Filme filme1 = FilmeBuilder.umFilme().constroi();


        IllegalArgumentException exception =

                assertThrows(IllegalArgumentException.class,
                             () -> locacaoService.alugarFilmes(joao, filme),
                             "Deveria ter lançado um IllegalArgumentException");

        assertTrue(exception.getMessage().contains("Filme sem Estoque"));


    }



}
