package dcomp.es2.locadora.repositorio;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.*;


import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.*;

public class UsuarioRepositoryTest {

	private EntityManager manager;
	private static EntityManagerFactory emf;
	private UsuarioRepository usuarios;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraPU_test");
	}

	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		usuarios = new UsuarioRepositoryImpl(manager);
	}

	@AfterEach
	public void depois() {
		manager.getTransaction().rollback();
	}

	@AfterAll
	public static void fim() {
		emf.close();
	}


	@Test
	public void deveEncontrarUsuarioPeloNome() {

		assertThrows(NoResultException.class,
				                () -> usuarios.buscaPorNome("João da Silva"),
				                "Deveria lançar a exceção NoResultException");

		usuarios.salva(new Usuario("João da Silva") );
		manager.flush();
		manager.clear();

		Usuario usuarioDoBanco = usuarios.buscaPorNome("João da Silva");

		assertThat("João da Silva", is(equalTo(usuarioDoBanco.getNome())));
	}

	@Test
	public void naoDeveEncontrarUsuarioPeloNome() {

		assertThrows(NoResultException.class,
				() -> usuarios.buscaPorNome("Pedro Jose"),
				"Deveria ter lançado a exceção NoResultException");
	}

	@Test
	public void deveExcluirUmUsuario() {
		Usuario usuario = new Usuario("João Carlos");

		usuarios.salva(usuario);
	    usuarios.exclui(usuario);


		manager.flush();
		manager.clear();

		assertThrows(NoResultException.class,
				() -> usuarios.buscaPorNome("João Carlos"),
				"Deveria ter lançado a exceção NoResultException");

	}

	@Test
	public void deveAlterarUmUsuario() {
		Usuario usuario = new Usuario("João Carlos");

		usuarios.salva(usuario);
    	usuario.setNome("João da Silva");

		usuarios.atualiza(usuario);

		manager.flush();
		manager.clear();

		Usuario novoUsuario = usuarios.buscaPorNome("João da Silva" );
		Assertions.assertNotNull(novoUsuario);
		
		assertThrows(NoResultException.class,
				() -> usuarios.buscaPorNome("João Carlos"),
				"Deveria ter lançado a exceção NoResultException");
	}

}
