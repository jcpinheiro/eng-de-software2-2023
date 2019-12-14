package integracao.rest.contatos;

import java.util.List;
import java.util.Optional;

public interface ContatoService {

	List<Contato> buscarContatos();

	Optional<Contato> buscarContato(Long id);

	Contato inserirOuAlterar(Contato contato);

	void remover(Long id);

}
