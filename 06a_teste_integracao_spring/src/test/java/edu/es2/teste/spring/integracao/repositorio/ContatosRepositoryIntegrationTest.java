package edu.es2.teste.spring.integracao.repositorio;

import java.util.List;

import javax.validation.ConstraintViolationException;

import edu.es2.teste.spring.integracao.modelo.Contato;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertThrows;


/*
A anotação @DataJpaTest aplica apenas faz
a configuração relevante aos testes JPA.
Por padrão, os testes anotados com @DataJpaTest
usam um banco de dados em memória. */

//@RunWith(SpringRunner.class)
@DataJpaTest
//@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.)
public class ContatosRepositoryIntegrationTest {

	@Autowired
	private ContatoRepository contatoRepository;


	private Contato contato;

	@BeforeEach
	public void start() {
		contato = new Contato("Chefe", "98", "123456789");
	}

	@Test
	public void saveComDddNuloDeveLancarException() throws Exception {

		ConstraintViolationException exception =
				assertThrows(ConstraintViolationException.class,
				() -> {  contato.setDdd(null);
			       	     contatoRepository.save(contato);
					  },
				"Deveria lançar um ConstraintViolationException");

		Assertions.assertTrue(exception.getMessage().contains("O DDD deve ser preenchido"));
	}

	@Test
	public void saveComTelefoneNuloDeveLancarException() throws Exception {

		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
				() -> {  contato.setTelefone(null);
					contatoRepository.save(contato);
				},
				"Deveria lançar um ConstraintViolationException");

		Assertions.assertTrue(exception.getMessage().contains("O Telefone deve ser preenchido"));

	}

	@Test
	public void saveComNomeNuloDeveLancarException() throws Exception {

		ConstraintViolationException exception = assertThrows(ConstraintViolationException.class,
				() -> {  contato.setNome(null);
					contatoRepository.save(contato);
				},
				"Deveria lançar um ConstraintViolationException");

		Assertions.assertTrue(exception.getMessage().contains("O Nome deve ser preenchido"));
	}

	@Test
	public void deveSalvarUmNovoContato() {

		contatoRepository.save(contato);

		List<Contato> contatos = contatoRepository.findAll();
		Assertions.assertEquals(1, contatos.size() );

		contatoRepository.deleteAll();
	}

	@Test
	public void deveRemoverUmContato() {

		contatoRepository.save(contato);
		List<Contato> contatos = contatoRepository.findAll();
		Assertions.assertEquals(1, contatos.size());

		contatoRepository.deleteById(contato.getId());
		List<Contato> resultado = contatoRepository.findAll();
		Assertions.assertEquals(0, resultado.size());
	}

}