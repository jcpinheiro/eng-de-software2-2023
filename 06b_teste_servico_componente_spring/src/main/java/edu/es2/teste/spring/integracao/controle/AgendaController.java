package edu.es2.teste.spring.integracao.controle;

import edu.es2.teste.spring.integracao.modelo.Contato;
import edu.es2.teste.spring.integracao.servico.ContatoException;
import edu.es2.teste.spring.integracao.servico.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class AgendaController {

	@Autowired
	private ContatoService contatoService;

	public void inseriRegistro(String nome, String ddd, String telefone) throws ContatoException {
		Contato contato = new Contato(nome, ddd, telefone);
		contatoService.adiciona(contato );
	}

	public void removeRegistro(Long id) {
		contatoService.remove(id );
	}

}
