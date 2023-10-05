package com.argentum.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class CandlestickTest {
	private LocalDateTime hoje;

	@BeforeEach
	public void setup() {
		hoje = LocalDateTime.now();

	}

	@Test
	public void maximoNaoDeveSerMenorDoQueMinimo(){
		

		CandleBuilder builder = new CandleBuilder();

		IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
				() -> builder.comAbertura(10.0).comFechamento(30.0)
						.comMinimo(25.0).comMaximo(15.0)
						.comVolume(200.0).comData(hoje)
						.geraCandle());

		Assertions.assertTrue(exception.getMessage().contains("O Maximo nao deve ser menor que minimo"));
	}
	
	@Test
	public void ehBaixaSeFechamentoForMenorDoQueAbetura(){
		

		CandleBuilder builder = new CandleBuilder();
		
		Candlestick candle = builder.comAbertura(30.0).comFechamento(20.0)
									.comMinimo(10.0).comMaximo(50.0)
									.comVolume(200.0).comData(hoje)
									.geraCandle();	
		
		Assertions.assertTrue(candle.isBaixa());
		
	}

	@Test
	public void ehAltaSeFechamentoForIgualAbetura(){

		LocalDateTime hoje = LocalDateTime.now();

		CandleBuilder builder = new CandleBuilder();

		Candlestick candle = builder.comAbertura(30.0).comFechamento(30.0)
				.comMinimo(10.0).comMaximo(50.0)
				.comVolume(200.0).comData(hoje)
				.geraCandle();

		Assertions.assertTrue(candle.isAlta());

	}

}
