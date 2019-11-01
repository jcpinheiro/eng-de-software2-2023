
package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class LocacaoServiceTest {

    @Test
    public void deveAlugarFilme() {

        // cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario joao = new Usuario("Joao");
        Filme filme = new Filme("Batman o Retorno", 3, 6.50);

        // ação
        Locacao locacao = locacaoService.alugarFilme(joao, filme);

        // verificação
        assertEquals(locacao.getValor(),6.5, 0.00001 );

        assertEquals(locacao.getDataLocacao(), LocalDate.now() );

        assertTrue(locacao.getDataRetorno().equals(LocalDate.now().plusDays(1)) );

    }

   /* @Test (expected = IllegalStateException.class)
    public void naoDeveAlugarFilmeSemEstoque() {

        // cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario joao = new Usuario("Joao");
        Filme filme = new Filme("Batman o Retorno", 0, 6.50);

        // ação
        Locacao locacao = locacaoService.alugarFilme(joao, filme);

    }
*/

    @Test
    public void naoDeveAlugarFilmeSemEstoque() {

        // cenário
        LocacaoService locacaoService = new LocacaoService();
        Usuario joao = new Usuario("Joao");
        Filme filme = new Filme("Batman o Retorno", 0, 6.50);

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                                                          () -> locacaoService.alugarFilme(joao, filme),
                "Deveria ter lançado um IllegalArgumentException");

        assertTrue(exception.getMessage().contains("Filme sem Estoque"));

    }



}
