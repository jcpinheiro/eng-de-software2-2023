package com.argentum.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;


public class NegociacaoTest {

	@Test
	public void naoDeveCriarNegociacaoComPrecoNegativo() {
		IllegalArgumentException exception =
				assertThrows(IllegalArgumentException.class,
				() -> new Negociacao(-0.01, 2, LocalDateTime.now()));

		Assertions.assertTrue(exception
				              .getMessage()
				               .contains("O preco nao pode ser negativo"));


	}
	
	@Test
	public void naoDeveCriarNegociacaoComDataNula() {
		assertThrows(IllegalArgumentException.class,
						      () -> new Negociacao(10.0, 2, null) );

	}

	@Test
	public void naoDeveCriarNegociacaoComQuantidadeNegativa() {
		assertThrows(IllegalArgumentException.class,
				                () -> new Negociacao(10.0, -1, LocalDateTime.now()) );
	}
	
	@Test
	public void mesmoSegundoEhMesmoDia(){
		LocalDateTime hoje = LocalDateTime.now();
		LocalDateTime agora = hoje;
		
		Negociacao negociacao = new Negociacao(100.0, 20, hoje);
		
		Assertions.assertTrue(negociacao.isMesmoDia(agora) );
		
	}
	
	@Test
	public void horariosDiferentesEhMesmoDia(){

		LocalDateTime hoje  = LocalDateTime.of(2022, 04, 04, 12, 00);
		LocalDateTime agora = LocalDateTime.of(2022, 04, 04, 13, 00);

		Negociacao negociacao = new Negociacao(100.0, 20, hoje);
		
		Assertions.assertTrue(negociacao.isMesmoDia(agora));
		
	}
	
	@Test
	public void mesesDiferentesNaoEhMesmoDia(){
		LocalDateTime hoje  = LocalDateTime.of(2022, 04, 04, 12, 00);
		LocalDateTime agora = LocalDateTime.of(2022, 03, 04, 13, 00);
		
		Negociacao negociacao = new Negociacao(100.0, 20, hoje);
		
		Assertions.assertFalse(negociacao.isMesmoDia(agora));
		
	}
	
	@Test
	public void anosDiferentesNaoEhMesmoDia(){

		LocalDateTime hoje  = LocalDateTime.of(2021, 04, 04, 12, 00);
		LocalDateTime agora = LocalDateTime.of(2022, 04, 04, 13, 00);
		
		Negociacao negociacao = new Negociacao(100.0, 20, hoje);
		
		Assertions.assertFalse(negociacao.isMesmoDia(agora));
	}

}
