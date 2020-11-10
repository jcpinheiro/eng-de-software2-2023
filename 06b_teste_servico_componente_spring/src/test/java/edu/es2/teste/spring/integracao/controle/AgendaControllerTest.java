package edu.es2.teste.spring.integracao.controle;

import javax.validation.ConstraintViolationException;

import edu.es2.teste.spring.integracao.modelo.Contato;
import edu.es2.teste.spring.integracao.repositorio.ContatoRepository;
import edu.es2.teste.spring.integracao.servico.ContatoException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class AgendaControllerTest {

	@MockBean
	private ContatoRepository contatoRepository;

	@Autowired
	private AgendaController agendaController;

	private String nome = "Chefe";

	private String ddd = "98";

	private String telefone = "222456766";

	@Test
	public void inserirRegistroComDddNuloDeveLancarException() throws ContatoException {


		Mockito.when(contatoRepository.save( Mockito.any()))
		.thenThrow(new ConstraintViolationException("O DDD deve ser preenchido",null));



		ContatoException exception =
				Assertions.assertThrows(ContatoException.class,
				  () -> agendaController.inseriRegistro(nome, null, telefone),
				"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O DDD deve ser preenchido"));
	}

	@Test
	public void inserirRegistroComTelefoneNuloDeveLancarException() throws ContatoException {

		Mockito.when(contatoRepository.save(Mockito.any()) )
	           .thenThrow(new ConstraintViolationException("O Telefone deve ser preenchido",null));

		ContatoException exception =
				Assertions.assertThrows(ContatoException.class,
						() -> agendaController.inseriRegistro(nome, ddd, null),
						"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O Telefone deve ser preenchido"));

	}

	@Test
	public void inserirRegistroComNomeNuloDeveLancarException() throws ContatoException {

		Mockito.when(contatoRepository.save((Contato)Mockito.any()))
		.thenThrow(new ConstraintViolationException("O Nome deve ser preenchido",null));

		ContatoException exception =
				Assertions.assertThrows(ContatoException.class,
						() -> agendaController.inseriRegistro(null, ddd, telefone),

						"Deveria lançar um ContatoException");

		Assertions.assertTrue(exception.getMessage().contains("O Nome deve ser preenchido"));

	}

	@Test
	public void deveSalvarUmNovoContato() throws ContatoException {
		agendaController.inseriRegistro(nome, ddd, telefone);

		Mockito.verify(contatoRepository, Mockito.times(1))
						.save(new Contato(nome, ddd, telefone));
	}

	@Test
	public void deveRemoverContato() {
		agendaController.removeRegistro(1L);
		Mockito.verify(contatoRepository,
				Mockito.times(1))
				.deleteById(1L);
	}

}