package dcomp.es2.locadora.servico;

import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class LocacaoServiceTeste {


    @Test
    public void testaLocacao() {
        //cenario
        LocacaoService service = new LocacaoService();
        Usuario usuario = new Usuario("Usuario 1");
        Filme filme = new Filme("Filme 1", 2, 5.0);

        //acao
        Locacao locacao = service.alugarFilmes(usuario, filme);

        //verificacao
        Assertions.assertTrue(locacao.getValor() == 5.0);
        Assertions.assertTrue(locacao.getDataLocacao().equals(LocalDate.now()) );
        Assertions.assertTrue(locacao.getDataRetorno().equals(LocalDate.now().plusDays(1)) );
    }
}
