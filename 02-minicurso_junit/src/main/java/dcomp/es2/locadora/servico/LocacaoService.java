package dcomp.es2.locadora.servico;

import java.time.LocalDate;
import java.util.Arrays;

import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;


public class LocacaoService {

	// TODO Preciso remover este código
    /*	public Locacao alugarFilme(Usuario usuario, Filme filme) {

		if (filme.getEstoque() <= 0 ) {
			throw new IllegalArgumentException("Filme sem Estoque ");
		}
		
		return alugarFilmes(usuario, Arrays.asList(filme) );
	}*/

	public Locacao alugarFilmes(Usuario usuario, Filme... filmes) {

		Arrays.stream(filmes).
				forEach(filme -> {
			      if (filme.getEstoque() <= 0)
				      throw new IllegalArgumentException("Filme sem Estoque: " + filme.getNome() );
		          });

			Locacao locacao = new Locacao();
			locacao.adiciona(filmes );
			locacao.setUsuario(usuario);
			locacao.setDataLocacao(LocalDate.now() );

			locacao.setDataRetorno(LocalDate.now().plusDays(1) );

			double valorTotal = calculaValorDaLocacao(filmes);

			locacao.setValor(valorTotal);


			//Salvando a locacao...
			//TODO adicionar método para salvar
			//daoLocacao.salva(locacao);

			return locacao;
		}


		private double calculaValorDaLocacao(Filme ...filmes) {

			double valorTotal = 0d;

			for(int i=1; i <= filmes.length; i++) {

				double valorFilme = filmes[i-1].getPrecoLocacao();

				switch(i) {
					case 2: valorFilme = valorFilme * 0.90; break;
					case 3: valorFilme = valorFilme * 0.70; break;
					case 4: valorFilme = valorFilme * 0.50; break;
				}

				valorTotal += valorFilme;
			}
			return valorTotal;
		}


}