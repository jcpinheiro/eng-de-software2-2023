package integracao.rest.contatos;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContatoServiceImpl implements ContatoService{

	@Autowired
	private ContatoRepository contatoRepository;

	@Override
	public List<Contato> buscarContatos() throws RuntimeException {
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
