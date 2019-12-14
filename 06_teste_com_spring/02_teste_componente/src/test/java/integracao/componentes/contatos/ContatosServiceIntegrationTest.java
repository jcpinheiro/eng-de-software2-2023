package integracao.componentes.contatos;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContatosServiceIntegrationTest {

	@Autowired
	private ContatoService contatoService;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	@Rule
	public ExpectedException expectedException = ExpectedException.none();


	@Before
	public void start() {
	   contato = new Contato("Chefe", "0y", "9xxxxxxx9");
	}

	@Test
	public void insereComDDDNuloDeveLancarException() throws Exception {
		expectedException.expect(ContatoException.class);
		expectedException.expectMessage("O DDD deve ser preenchido");

		contato.setDdd(null);
		contatoService.inserir(contato );
	}

	@Test
	public void inserirComTelefoneNuloDeveLancarException() throws Exception {
		expectedException.expect(ContatoException.class);
		expectedException.expectMessage("O Telefone deve ser preenchido");

		contato.setTelefone(null);
		contatoService.inserir(contato);
	}

	@Test
	public void inserirComNomeNuloDeveLancarException() throws Exception {
		expectedException.expect(ContatoException.class);
		expectedException.expectMessage("O Nome deve ser preenchido");

		contato.setNome(null);
		contatoService.inserir(contato );
	}

	@Test
	public void devSalvarContato() throws ContatoException {

		contatoService.inserir(contato );
		List<Contato> contatos = contatoRepository.findAll();
		Assert.assertEquals(1, contatos.size());
		contatoRepository.deleteAll();
	}

	@Test
	public void deveRemoverContato() {
		contatoRepository.save(contato);
		List<Contato> contatos = contatoRepository.findAll();
		Assert.assertEquals(1, contatos.size());

		contatoService.remover(contato.getId() );
		List<Contato> resultado = contatoRepository.findAll();
		Assert.assertEquals(0, resultado.size());
	}

}