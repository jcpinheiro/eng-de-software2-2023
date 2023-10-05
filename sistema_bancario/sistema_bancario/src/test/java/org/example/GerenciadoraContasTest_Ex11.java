package org.example;

import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


public class GerenciadoraContasTest_Ex11 {

	private GerenciadoraContas gerContas;
	
	
	@Test
	public void testTransfereValor() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, 200, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 100, idConta02);
		
		/* ========== Vefificações ========== */
		assertTrue(sucesso);
		assertThat(conta02.getSaldo(), is(100.0));
		assertThat(conta01.getSaldo(), is(100.0));
	}
	
	
	@Test
	public void testTransfereValor_SaldoInsuficiente() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, 100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertThat(conta01.getSaldo(), is(-100.0));
		assertThat(conta02.getSaldo(), is(200.0));
	}

	
	@Test
	public void testTransfereValor_SaldoNegativo() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, 0, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Verificações ========== */
		assertTrue(sucesso);
		assertThat(conta01.getSaldo(), is(-300.0));
		assertThat(conta02.getSaldo(), is(200.0));
	}


	@Test
	public void testTransfereValor_SaldoNegativoParaNegativo() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, -100, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 200, idConta02);
		
		/* ========== Vefificações ========== */
		assertTrue(sucesso);
		assertThat(conta01.getSaldo(), is(-300.0));
		assertThat(conta02.getSaldo(), is(100.0));
	}
	
	@Test
	public void testTransfereValor_Nenhum() {

		/* ========== Montagem do cenário ========== */
		
		// criando alguns clientes
		int idConta01 = 1;
		int idConta02 = 2;
		ContaCorrente conta01 = new ContaCorrente(idConta01, -100, true);
		ContaCorrente conta02 = new ContaCorrente(idConta02, -100, true);
		
		// inserindo os clientes criados na lista de clientes do banco
		List<ContaCorrente> contasDoBanco = new ArrayList<>();
		contasDoBanco.add(conta01);
		contasDoBanco.add(conta02);
		
		gerContas = new GerenciadoraContas(contasDoBanco);

		/* ========== Execução ========== */
		boolean sucesso = gerContas.transfereValor(idConta01, 2, idConta02);
		
		/* ========== Vefificações ========== */
		assertTrue(sucesso);
		assertThat(conta01.getSaldo(), is(-102.0));
		assertThat(conta02.getSaldo(), is(-98.0));
	}
}