package integracao.rest.agenda;

import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;

import integracao.rest.contatos.Contato;
import integracao.rest.contatos.ContatoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class AgendaControllerIntegrationTest {

	// serve para consumir os métodos HTTP
	@Autowired
	private TestRestTemplate testRestTemplate;

	@Autowired
	private ContatoRepository contatoRepository;

	private Contato contato;

	private String nome = "Chefe";
	private String ddd = "98";
	private String telefone = "22233334";

	@Before
	public void start() {
		contato = new Contato(nome, ddd, telefone);
		contatoRepository.save(contato);
	}

	@After
	public void end() {
		contatoRepository.deleteAll();
	}


	@Test
	public void deveMostrarTodosContatos() {
		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/agenda/",HttpMethod.GET, null, String.class);

		System.out.println("######## " + resposta.getBody() );
		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}


	@Test
	public void deveMostrarTodosContatosUsandoString() {
		ResponseEntity<String> resposta =
				testRestTemplate.exchange("/agenda/",HttpMethod.GET,null, String.class);

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));

		String result = "[{\"id\":"+contato.getId()+",\"ddd\":\"98\","
				+ "\"telefone\":\"22233334\",\"nome\":\"Chefe\"}]";
		Assert.assertEquals(result, resposta.getBody());
	}

	@Test
	public void deveMostrarTodosContatosUsandoList() {
		ParameterizedTypeReference<List<Contato>> tipoRetorno =	new ParameterizedTypeReference<List<Contato>>() {};

		ResponseEntity<List<Contato>> resposta =
				testRestTemplate.exchange("/agenda/",HttpMethod.GET,null, tipoRetorno);

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));
		Assert.assertEquals(1, resposta.getBody().size());
		Assert.assertEquals(contato, resposta.getBody().get(0));
	}

	@Test
	public void deveMostrarUmContato() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/contato/{id}",HttpMethod.GET,null, Contato.class,contato.getId() );

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));
		Assert.assertEquals(contato, resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontrado() {

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/contato/{id}",HttpMethod.GET,null, Contato.class,100 );

		Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

    // --------------------- GET --------------------
	@Test
	public void deveMostrarUmContatoComGetForEntity() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,contato.getId());

		Assert.assertEquals(HttpStatus.OK, resposta.getStatusCode());
		Assert.assertTrue(resposta.getHeaders().getContentType().equals(
				MediaType.parseMediaType("application/json;charset=UTF-8")));
		Assert.assertEquals(contato, resposta.getBody());
	}

	@Test
	public void deveMostrarUmContatoComGetForObject() {
		Contato resposta =
				testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class,contato.getId());
		Assert.assertEquals(contato, resposta);
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradoComGetForEntity() {
		ResponseEntity<Contato> resposta =
				testRestTemplate.getForEntity("/agenda/contato/{id}", Contato.class,100);

		Assert.assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

	@Test
	public void buscaUmContatoDeveRetornarNaoEncontradogetForObject() {
		Contato resposta = testRestTemplate.getForObject("/agenda/contato/{id}", Contato.class,100);
		Assert.assertNull(resposta);
	}

// ----------------------- POST -------------------------

	@Test
	public void salvarContatoDeveRetornarMensagemDeErro() {
		Contato contato = new Contato(nome, null, null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);

		ResponseEntity<List<String>> resposta =
				testRestTemplate.exchange("/agenda/inserir",
						                  HttpMethod.POST,httpEntity,
						                  new ParameterizedTypeReference<List<String>>() {});

		Assert.assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		Assert.assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		Assert.assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void inserirDeveSalvarContato() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);

		ResponseEntity<Contato> resposta =
				testRestTemplate.exchange("/agenda/inserir",HttpMethod.POST,httpEntity, Contato.class);

		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		Assert.assertNotNull(resultado.getId());
		Assert.assertEquals(contato.getNome(), resultado.getNome());
		Assert.assertEquals(contato.getDdd(), resultado.getDdd());
		Assert.assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void inserirDeveSalvarContatoComPostForEntity() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta =
				testRestTemplate.postForEntity("/agenda/inserir",httpEntity, Contato.class);

		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());

		Contato resultado = resposta.getBody();

		Assert.assertNotNull(resultado.getId());
		Assert.assertEquals(contato.getNome(), resultado.getNome());
		Assert.assertEquals(contato.getDdd(), resultado.getDdd());
		Assert.assertEquals(contato.getTelefone(), resultado.getTelefone());
		contatoRepository.deleteAll();
	}

	@Test
	public void inserirContatoDeveSalvarContatoPostForObject() {
		Contato contato = new Contato(nome, ddd, telefone);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		Contato resposta = testRestTemplate.postForObject("/agenda/inserir",httpEntity, Contato.class);

		Assert.assertNotNull(resposta.getId());
		Assert.assertEquals(contato.getNome(), resposta.getNome());
		Assert.assertEquals(contato.getDdd(), resposta.getDdd());
		Assert.assertEquals(contato.getTelefone(), resposta.getTelefone());
		contatoRepository.deleteAll();
	}



	// ------------------- PUTS e DELETE ------------------------------
	@Test
	public void alterarDeveRetornarMensagemDeErro() {
		contato.setDdd(null);
		contato.setTelefone(null);
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<List<String>> resposta = 
				testRestTemplate.exchange("/agenda/alterar/{id}",HttpMethod.PUT
						,httpEntity, new ParameterizedTypeReference<List<String>>() {},contato.getId());
		
		Assert.assertEquals(HttpStatus.BAD_REQUEST,resposta.getStatusCode());
		Assert.assertTrue(resposta.getBody().contains("O DDD deve ser preenchido"));
		Assert.assertTrue(resposta.getBody().contains("O Telefone deve ser preenchido"));
	}

	@Test
	public void alterarDeveAlterarContato() {
		contato.setNome("Novo Chefe");
		HttpEntity<Contato> httpEntity = new HttpEntity<>(contato);
		ResponseEntity<Contato> resposta = 
				testRestTemplate.exchange("/agenda/alterar/{id}",HttpMethod.PUT,httpEntity
						, Contato.class,contato.getId());
		
		Assert.assertEquals(HttpStatus.CREATED,resposta.getStatusCode());
		Contato resultado = resposta.getBody(); 
		Assert.assertEquals(contato.getId(), resultado.getId());
		Assert.assertEquals(ddd, resultado.getDdd());
		Assert.assertEquals(telefone, resultado.getTelefone());
		Assert.assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void alterarDeveAlterarContatoComPut() {
		contato.setNome("Novo Chefe");
		testRestTemplate.put("/agenda/alterar/{id}",contato,contato.getId());

		Contato resultado = contatoRepository.findById(contato.getId()).get();
		Assert.assertEquals(ddd, resultado.getDdd());
		Assert.assertEquals(telefone, resultado.getTelefone());
		Assert.assertEquals("Novo Chefe", resultado.getNome());
	}

	@Test
	public void removerDeveExcluirContato() {
		ResponseEntity<Contato> resposta = 
				testRestTemplate.exchange("/agenda/remover/{id}",HttpMethod.DELETE,null
						, Contato.class,contato.getId());
		
		Assert.assertEquals(HttpStatus.NO_CONTENT,resposta.getStatusCode());
		Assert.assertNull(resposta.getBody());
	}

	@Test
	public void removerDeveExcluirContatoComDelete() {
		testRestTemplate.delete("/agenda/remover/"+contato.getId());
		
		Optional<Contato> resultado = contatoRepository.findById(contato.getId());
		Assert.assertEquals(Optional.empty(), resultado);
	}

}