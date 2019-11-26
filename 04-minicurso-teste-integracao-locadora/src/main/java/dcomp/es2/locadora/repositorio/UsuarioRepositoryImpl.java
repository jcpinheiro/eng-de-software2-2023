package dcomp.es2.locadora.repositorio;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import dcomp.es2.locadora.modelo.Usuario;

public class UsuarioRepositoryImpl implements UsuarioRepository {

	private EntityManager manager;

	public UsuarioRepositoryImpl(EntityManager manager) {
		this.manager = manager;
	}

	@Override
	public Usuario buscaPorNome(String nome) {

		return manager.createQuery("SELECT u FROM Usuario u WHERE u.nome = :pNome", Usuario.class)
				.setParameter("pNome", nome)
				.getSingleResult();

	}

	@Override
	public void salva(Usuario usuario) {
		manager.persist(usuario);

	}

	@Override
	public void exclui(Usuario usuario) {
		manager.remove(usuario);
	}

	@Override
	public void atualiza(Usuario usuario) {
		manager.merge(usuario );
		
	}

}
