package org.example;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.List;


public class GerenciadoraClientesTest_Ex4 {

	private GerenciadoraClientes gerClientes;



	@Test
	@DisplayName("Teste básico da pesquisa de um cliente a partir do seu ID.")
	public void testPesquisaCliente() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);

		/* ========== Execuçõo ========== */
		Cliente cliente = gerClientes.pesquisaCliente(1);
		
		/* ========== Verificação ========== */
		assertThat(cliente.getId(), is(1));
		
	}
	
	@Test
	public void testRemoveCliente() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		Cliente cliente01 = new Cliente(1, "Gustavo Farias", 31, "gugafarias@gmail.com", 1, true);
		Cliente cliente02 = new Cliente(2, "Felipe Augusto", 34, "felipeaugusto@gmail.com", 1, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<Cliente> clientesDoBanco = new ArrayList<>();
		clientesDoBanco.add(cliente01);
		clientesDoBanco.add(cliente02);
		
		gerClientes = new GerenciadoraClientes(clientesDoBanco);
		
		/* ========== Execução ========== */
		boolean clienteRemovido = gerClientes.removeCliente(2);
		
		/* ========== Verificações ========== */
		assertThat(clienteRemovido, is(true));
		assertThat(gerClientes.getClientesDoBanco().size(), is(1));
		Assertions.assertNull(gerClientes.pesquisaCliente(2));
		
	}

}
