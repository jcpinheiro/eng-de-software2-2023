package edu.es2.teste.spring.integracao.servico;


import edu.es2.teste.spring.integracao.modelo.Contato;
import edu.es2.teste.spring.integracao.repositorio.ContatoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.validation.ConstraintViolationException;
import java.util.List;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class ContatosServiceTest {

	@Autowired
	private ContatoService contatoService;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	@BeforeEach
	public void before() {
	   contato = new Contato("Chefe", "098", "998877665");
	}

	@Test
	public void insereComDDDNuloDeveLancarException()  {

		ContatoException exception = Assertions.assertThrows(ContatoException.class,
				() -> {  contato.setDdd(null);
					    contatoService.adiciona(contato );
				},
				"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O DDD deve ser preenchido"));

	}

	@Test
	public void inserirComTelefoneNuloDeveLancarException() throws Exception {

		ContatoException exception = Assertions.assertThrows(ContatoException.class,
				() -> {
			        contato.setTelefone(null);
					contatoService.adiciona(contato);
				},
				"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O Telefone deve ser preenchido"));

	}

	@Test
	public void inserirComNomeNuloDeveLancarException()  {

		ContatoException exception = Assertions.assertThrows(ContatoException.class,
				() -> {
					contato.setNome(null);
					contatoService.adiciona(contato );
				},
				"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O Nome deve ser preenchido"));

	}

	@Test
	public void deveSalvarUmNovoContato() throws ContatoException  {

		contatoService.adiciona(contato );
		List<Contato> contatos = contatoRepository.findAll();
		Assertions.assertEquals(1, contatos.size());
		contatoRepository.deleteAll();
	}

	@Test
	public void deveRemoverContato() {
		contatoRepository.save(contato);
		List<Contato> contatos = contatoRepository.findAll();
		Assertions.assertEquals(1, contatos.size());

		contatoService.remove(contato.getId() );
		List<Contato> resultado = contatoRepository.findAll();
		Assertions.assertEquals(0, resultado.size());
	}

}