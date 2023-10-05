package edu.es2.teste.spring.restcontroller.controle;

import edu.es2.teste.spring.restcontroller.modelo.Contato;
import edu.es2.teste.spring.restcontroller.repositorio.ContatoRepository;
import org.assertj.core.api.AbstractStringAssert;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AgendaControllerComRestTemplateIntegrationTest {

	// serve para consumir os métodos HTTP
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	private String nome = "Chefe";
	private String ddd = "98";
	private String telefone = "22233334";

	@BeforeEach
	public void start() {
		contato = new Contato(nome, ddd, telefone);
		contatoRepository.save(contato );
	}

	@AfterEach
	public void end() {
		contatoRepository.deleteAll();
	}


	@Test
	public void deveMostrarTodosContatos() {
		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/agenda/",HttpMethod.GET,
						                   null, String.class);
		System.out.println("######## " + resposta.getBody() );

		Assertions.assertThat(resposta.getStatusCode()).isEqualTo(HttpStatus.OK);
	}


	@Test
	public void deveMostrarTodosContatosUsandoString() {
		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/agenda/", HttpMethod.GET, null, String.class);

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json"));

		String result = "[{\"id\":"+contato.getId()+",\"ddd\":\"98\","
				+ "\"telefone\":\"22233334\",\"nome\":\"Chefe\"}]";
		assertEquals(result, resposta.getBody() );
	}

	@Test
	public void deveMostrarTodosContatosUsandoList() {
		ParameterizedTypeReference<List<Contato>> tipoRetorno =
				new ParameterizedTypeReference<List<Contato>>() {};

		ResponseEntity<List<Contato>> resposta =
				testRestTemplate.exchange("/agenda/",
						                   HttpMethod.GET,null,
						                   tipoRetorno );

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json") );
		assertEquals(1, resposta.getBody().size());
		assertEquals(contato, resposta.getBody().get(0));
	}

	@Test
	public void deveBuscarUmContatoPeloID() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/{id}",
						                   HttpMethod.GET,null,
						                   Contato.class, contato.getId() );

		assertEquals(HttpStatus.OK, resposta.getStatusCode());
		assertEquals(resposta.getHeaders().getContentType(),
				MediaType.parseMediaType("application/json"));
		assertEquals(contato, resposta.getBody());
	}

	@Test
	public void deveRetornar404QuandoContatoNaoForEncontrado() {

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/{id}",
						                   HttpMethod.GET,
						                   null,
						                    Contato.class,
						                   100 );

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	// --------------------- GET --------------------
	@Test
	public void deveMostrarUmContatoComGetForEntity() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.getForEntity("/agenda/{id}",
						                       Contato.class,contato.getId());

		assertEquals(HttpStatus.OK, resposta.getStatusCode());

		assertEquals(resposta.getHeaders().getContentType(),
				     MediaType.parseMediaType("application/json"));

		assertEquals(contato, resposta.getBody());
	}

	@Test
	public void deveMostrarUmContatoComGetForObject() {
		Contato resposta =
				testRestTemplate.getForObject("/agenda/{id}",
						                      Contato.class,contato.getId());
		assertEquals(contato, resposta);
	}

	@Test
	public void deveRetornarContatoNaoEncontradoComGetForEntity() {
		ResponseEntity<Contato> resposta;
		resposta = testRestTemplate.getForEntity("/agenda/{id}",
				                                 Contato.class,100);

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	public void naoDeveEncontrarContatoInexistente() {
		Contato resposta = testRestTemplate.getForObject("/agenda/{id}",
				                                          Contato.class,100);
		assertNull(resposta);
	}

// ----------------------- POST -------------------------

	@Test
	public void naoDeveSalvarContatoComErroDeValidacao() {
		Contato contato = new Contato(nome, null, null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);

		ResponseEntity<List<String>> resposta =
				testRestTemplate.exchange("/agenda",
						HttpMethod.POST,httpEntity,
						new ParameterizedTypeReference<List<String>>() {});

		assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void deveSalvarContato() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda",HttpMethod.POST,httpEntity, Contato.class);

		assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		assertNotNull(resultado.getId());
		assertEquals(contato.getNome(), resultado.getNome());
		assertEquals(contato.getDdd(), resultado.getDdd());
		assertEquals(contato.getTelefone(), resultado.getTelefone());
		//contatoRepository.deleteAll();
	}

	@Test
	public void deveSalvarContatoComPostForEntity() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta =
				testRestTemplate.postForEntity("/agenda",
						                       httpEntity, Contato.class);

		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		assertNotNull(resultado.getId());
		assertEquals(contato.getNome(), resultado.getNome());
		assertEquals(contato.getDdd(), resultado.getDdd());
		assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void deveSalvarContatoComPostForObject() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		Contato resposta = testRestTemplate.postForObject("/agenda",httpEntity, Contato.class);

		assertNotNull(resposta.getId());
		assertEquals(contato.getNome(), resposta.getNome());
		assertEquals(contato.getDdd(), resposta.getDdd());
		assertEquals(contato.getTelefone(), resposta.getTelefone());
	}



	// ------------------- PUTS e DELETE ------------------------------
	@Test
	public void deveRetornarMensagemDeErroQuandoAlterarContato() {
		contato.setDdd(null);
		contato.setTelefone(null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<List<String>> resposta =
				testRestTemplate.exchange("/agenda/{id}",HttpMethod.PUT
						,httpEntity,
						new ParameterizedTypeReference<List<String>>() {},
						contato.getId());

		assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void deveAlterarContato() {
		contato.setNome("Novo Chefe");
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/{id}",HttpMethod.PUT,
						  httpEntity
						, Contato.class,contato.getId());

		assertEquals(HttpStatus.OK,resposta.getStatusCode());
		Contato resultado = resposta.getBody();
		assertEquals(contato.getId(), resultado.getId());
		assertEquals(ddd, resultado.getDdd());
		assertEquals(telefone, resultado.getTelefone());
		assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void deveAlterarContatoComPut() {
		contato.setNome("Novo Chefe");
		testRestTemplate.put("/agenda/{id}",contato,contato.getId());

		Contato resultado = contatoRepository.findById(contato.getId()).get();
		assertEquals(ddd, resultado.getDdd());
		assertEquals(telefone, resultado.getTelefone());
		assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void deveExcluirContato() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/{id}",
						    HttpMethod.DELETE,null
						, Contato.class,contato.getId());

		assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
		assertNull(resposta.getBody());
	}

	@Test
	public void deveExcluirContatoComMetodoDelete() {
		testRestTemplate.delete("/agenda/"+contato.getId());

		final Optional<Contato> resultado = contatoRepository.findById(contato.getId());
		assertEquals(Optional.empty(), resultado);
	}

}