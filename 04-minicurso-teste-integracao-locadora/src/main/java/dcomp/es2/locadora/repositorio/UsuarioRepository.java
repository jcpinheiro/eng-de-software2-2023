package dcomp.es2.locadora.repositorio;

import dcomp.es2.locadora.modelo.Usuario;

public interface UsuarioRepository {

	Usuario buscaPorNome(String string);
	
	void salva(Usuario usuario);

	void exclui(Usuario usuario);

	void atualiza(Usuario usuario);
	
	

}
