package edu.es2.teste.spring.restcontroller.servico;


import edu.es2.teste.spring.restcontroller.modelo.Contato;
import edu.es2.teste.spring.restcontroller.repositorio.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContatoServiceImpl implements ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;

	@Override
	public List<Contato> buscarContatos() {
		return contatoRepository.findAll();

	}

	@Override
	public Optional<Contato> buscarContato(Long id) {
		return contatoRepository.findById(id);
	}

	@Override
	public Contato inserirOuAlterar(Contato contato) {
		return contatoRepository.save(contato);
	}

	@Override
	public void remover(Long id) {
		contatoRepository.deleteById(id);
	}

}
