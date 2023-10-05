package org.example;

import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


public class GerenciadoraClientesTest_Ex10 {

	private GerenciadoraClientes gerClientes;
	private int idCLiente01 = 1;
	private	int idCLiente02 = 2;
	
	@BeforeEach
	public void setUp() {
	
		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		Cliente cliente01 = new Cliente(idCLiente01, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(idCLiente02, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
	}

	@AfterEach
	public void tearDown() {
		gerClientes.limpa();
	}
	
	@Test
	public void testPesquisaCliente() {

		/* ========== Execução ========== */
		Cliente cliente = gerClientes.pesquisaCliente(idCLiente01);
		
		/* ========== Vefificações ========== */
		assertThat(cliente.getId(), is(idCLiente01));
		
	}
	

	@Test
	public void testPesquisaClienteInexistente() {

		/* ========== Execução ========== */
		Cliente cliente = gerClientes.pesquisaCliente(1001);
		
		/* ========== Vefificações ========== */
		assertNull(cliente);
		
	}
	
	@Test
	public void testRemoveCliente() {
		
		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(idCLiente02);
		
		/* ========== Vefificações ========== */
		assertThat(clienteRemovido, is(true));
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		assertNull(gerClientes.pesquisaCliente(idCLiente02));
		
	}

	@Test
	public void testRemoveClienteInexistente() {

		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(1001);
		
		/* ========== Vefificações ========== */
		assertThat(clienteRemovido, is(false));
		assertThat(gerClientes.getClientesDoBanco().size(), is(2));
		
	}
	

	@Test
	public void testClienteIdadeAceitavel()  {

		/* ========== Montagem do cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 25, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Vefificações ========== */
		assertTrue(idadeValida);	
	}
	

	@Test
	public void testClienteIdadeAceitavel_02()  {

		/* ========== Montagem do cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 18, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Vefificações ========== */
		assertTrue(idadeValida);	
	}
	

	@Test
	public void testClienteIdadeAceitavel_03() throws IdadeNaoPermitidaException {

		/* ========== Montagem do cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 65, "guga@gmail.com", 1, true);
		
		/* ========== Execução ========== */
		boolean idadeValida = gerClientes.validaIdade(cliente.getIdade());
		
		/* ========== Vefificações ========== */
		assertTrue(idadeValida);	
	}
	
	// Validação da idade de um cliente quando a mesma está abaixo intervalo permitido.
	@Test
	@DisplayName("Validação da idade de um cliente quando a mesma está abaixo intervalo permitido")
	public void testClienteMenorDeIdade() throws IdadeNaoPermitidaException {

		/* ========== Montagem do cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 17, "guga@gmail.com", 1, true);

		/* ========== Execução ========== */

		IdadeNaoPermitidaException
				exception = assertThrows(IdadeNaoPermitidaException.class,
				                         () -> gerClientes.validaIdade(cliente.getIdade()),
				                     "Esse testes falhou. A idade não permitida");


			assertThat(exception.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));

	}

	

	@Test
	public void testClienteIdadeAceitavel_05() throws IdadeNaoPermitidaException {
		
		/* ========== Montagem do cenário ========== */		
		Cliente cliente = new Cliente(1, "Gustavo", 66, "guga@gmail.com", 1, true);
		/* ========== Execução ========== */
		try {
			gerClientes.validaIdade(cliente.getIdade());
			fail("Idade acima do permitido");
		} catch (Exception e) {
			/* ========== Vefificações ========== */
			assertThat(e.getMessage(), is(IdadeNaoPermitidaException.MSG_IDADE_INVALIDA));
		}	
	}
	
}

