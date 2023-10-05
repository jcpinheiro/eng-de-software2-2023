package edu.es2.teste.spring.restcontroller.controle;

import edu.es2.teste.spring.restcontroller.modelo.Contato;
import edu.es2.teste.spring.restcontroller.repositorio.ContatoRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;

import java.net.URI;
import java.net.URISyntaxException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AgendaControllerComRestTemplateICRUDTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository contatoRepository;

	@AfterEach
	public void cleanUp() {
		contatoRepository.deleteAll();
	}


	@Test
	@Sql(statements = "INSERT INTO contato(id, nome, ddd, telefone) VALUES (1, 'Chefe', '98', '22334455')" )
	public void deveBuscarContatoPeloId() throws URISyntaxException {
		ResponseEntity<Contato> responseGetForEntity = testRestTemplate
				 .getForEntity(new URI("/agenda/1"), Contato.class);
		//testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,  contato.getId());

		Assertions.assertThat(responseGetForEntity.getBody().getNome()).isEqualTo("Chefe");

		Assertions.assertThat(responseGetForEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		Contato responseGetForObject = testRestTemplate.getForObject("/agenda/1", Contato.class);
		Assertions.assertThat(responseGetForObject.getNome()).isEqualTo("Chefe");
	}

	@Test
	public void deveCadastrarUmNovoContato() {
		ResponseEntity<Contato> responsePostForEntity =
				testRestTemplate.postForEntity("/agenda", new Contato("Joao da Silva", "98", "22334455"), Contato.class);

        Assertions.assertThat(responsePostForEntity.getBody().getNome()).isEqualTo("Joao da Silva");
		Assertions.assertThat(responsePostForEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED );
		Assertions.assertThat(responsePostForEntity.getHeaders().getLocation().getPath() )
				   .isEqualTo("/agenda/1");

		System.out.println("####### " + responsePostForEntity.getHeaders().getLocation() );

		/*Contato responsePostForObject = testRestTemplate.postForObject("/agenda",
				new Contato("Jose  da Silva", "98", "22334789"), Contato.class);
		Assertions.assertThat(responsePostForObject.getNome()).isEqualTo("Jose  da Silva");*/
	}

	@Test
	@Sql(statements = "INSERT INTO contato(id, nome, ddd, telefone) VALUES (1, 'Chefe', '98', '22334455')")
	public void deveAlterarUmContatoExistente() {
		testRestTemplate.put("/agenda/1", new Contato("Joao da Silva", "98", "22334455"));
		Contato contato = contatoRepository.findById(1L).get();

		Assertions.assertThat(contato.getNome()).isNotEqualTo("Chefe");
		Assertions.assertThat(contato.getNome()).isEqualTo("Joao da Silva");
	}

	@Test
	@Sql(statements = "INSERT INTO contato(id, nome, ddd, telefone) VALUES (1, 'Chefe', '98', '22334455')" )
	public void deveExcluirUmContatoExistente() {
		testRestTemplate.delete("/agenda/{id}", 1);

		Long count = contatoRepository.count();
		Assertions.assertThat(count).isEqualTo(0);
	}


}