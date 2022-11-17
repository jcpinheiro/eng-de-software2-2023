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
class TestRestTemplateIntegrationTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository personRepository;

	@AfterEach
	public void cleanUp() {
		personRepository.deleteAll();
	}


	@Test
	@Sql(statements = "INSERT INTO contato(id, nome, ddd, telefone) VALUES (1, 'Chefe', '98', '22334455')" )
	public void getTest() throws URISyntaxException {
		ResponseEntity<Contato> responseGetForEntity = testRestTemplate
				 .getForEntity(new URI("/agenda/contato/1"), Contato.class);

		//testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,contato.getId());

		Assertions.assertThat(responseGetForEntity.getBody().getNome()).isEqualTo("Chefe");

		Assertions.assertThat(responseGetForEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

		Contato responseGetForObject = testRestTemplate.getForObject("/agenda", Contato.class);
		Assertions.assertThat(responseGetForObject.getNome()).isEqualTo("Chefe");
	}

/*	@Test
	public void postTest() {
		ResponseEntity<Person> responsePostForEntity = testRestTemplate.postForEntity("/",
				new Person("John", 62), Person.class);
		Assertions.assertThat(responsePostForEntity.getBody().getName()).isEqualTo("John");
		Assertions.assertThat(responsePostForEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

		Person responsePostForObject = testRestTemplate.postForObject("/",
				new Person("Doe", 45), Person.class);
		Assertions.assertThat(responsePostForObject.getName()).isEqualTo("Doe");
	}

	@Test
	@Sql(statements = "INSERT INTO person (id, name, age) VALUES (99, 'Mark', 57)")
	public void putTest() {
		testRestTemplate.put("/", new Person(99,"Len", 57));

		Person person = personRepository.findById(99).get();
		Assertions.assertThat(person.getName()).isEqualTo("Len");
	}

	@Test
	@Sql(statements = "INSERT INTO person (id, name, age) VALUES (50, 'Len', 57)")
	public void deleteTest() {
		testRestTemplate.delete("/{id}", 50);

		Long count = personRepository.count();
		Assertions.assertThat(count).isEqualTo(0);
	}

	@Test
	@Sql(statements = "INSERT INTO person (name, age) VALUES ('Mark', 57)")
	public void exchangeTest() {
		ResponseEntity<Person> response = testRestTemplate.exchange("/", HttpMethod.GET,
				HttpEntity.EMPTY, Person.class);
		Assertions.assertThat(response.getBody().getName()).isEqualTo("Mark");
		Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
	}*/
}