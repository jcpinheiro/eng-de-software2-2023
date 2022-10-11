package com.argentum.modelo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

public class CandlestickTest {

	@Test
	public void maximoNaoDeveSerMenorDoQueMinimo(){
		
		LocalDateTime hoje = LocalDateTime.now();
		
		CandleBuilder builder = new CandleBuilder();

		Assertions.assertThrows(IllegalArgumentException.class,
				() ->  builder.comAbertura(10.0).comFechamento(30.0)
									.comMinimo(25.0).comMaximo(15.0)
									.comVolume(200.0).comData(hoje)
									.geraCandle() );
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
