package edu.es2.teste.spring.integracao.servico;

import javax.validation.ConstraintViolationException;

import edu.es2.teste.spring.integracao.modelo.Contato;
import edu.es2.teste.spring.integracao.repositorio.ContatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ContatoService {

	@Autowired
	private ContatoRepository contatoRepository;

	@Transactional
	public void adiciona(Contato contato) throws ContatoException {
		try {
			contatoRepository.save(contato);
		} catch (ConstraintViolationException e) {
			throw new ContatoException(e);
		}
	}

	@Transactional
	public void remove(Long id) {
		contatoRepository.deleteById(id);
	}

}
