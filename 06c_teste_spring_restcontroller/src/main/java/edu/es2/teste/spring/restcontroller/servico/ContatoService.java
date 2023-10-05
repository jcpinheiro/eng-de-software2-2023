package edu.es2.teste.spring.restcontroller.servico;

import edu.es2.teste.spring.restcontroller.modelo.Contato;

import java.util.List;
import java.util.Optional;

public interface ContatoService {

	List<Contato> buscarContatos();

	Optional<Contato> buscarContato(Long id);

	Contato inserirOuAlterar(Contato contato);

	void remover(Long id);

	boolean naoExisteContatoCom(Long id);
}
