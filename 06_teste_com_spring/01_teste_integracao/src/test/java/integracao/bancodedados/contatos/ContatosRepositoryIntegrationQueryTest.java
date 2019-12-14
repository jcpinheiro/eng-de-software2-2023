package integracao.bancodedados.contatos;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.List;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ContatosRepositoryIntegrationQueryTest {

	@Autowired
	private ContatoRepository contatoRepository;

	@Before
	public void before() {
		Contato contato = new Contato("Chefe", "98", "123456789");
		contatoRepository.save(contato );
		contato = new Contato("Novo Chefe", "98", "222123456");
		contatoRepository.save(contato );
		contato = new Contato("Chefe Mais Antigo", "98", "987654321");
		contatoRepository.save(contato );
		contato = new Contato("Amigo", "98", "222333444");
		contatoRepository.save(contato );
	}

	@After
	public void after() {
		contatoRepository.deleteAll();
	}


	@Test
	public void deveBuscarContatoPeloNome() {
		Contato contato= contatoRepository.findFirstByNome("Chefe");
		Assert.assertTrue(contato.getNome().equals("Chefe"));
	}

	@Test
	public void deveRetornarTodosOsContatosOrdenadosEmOrdemCrescente()  {
		List<Contato> contatos = contatoRepository.todos(new Sort(Sort.Direction.ASC, "nome"));

		Assert.assertTrue(contatos.get(0).getNome().equals("Amigo"));
		Assert.assertTrue(contatos.get(1).getNome().equals("Chefe"));
		Assert.assertTrue(contatos.get(2).getNome().equals("Chefe Mais Antigo"));
		Assert.assertTrue(contatos.get(3).getNome().equals("Novo Chefe"));

	}


}