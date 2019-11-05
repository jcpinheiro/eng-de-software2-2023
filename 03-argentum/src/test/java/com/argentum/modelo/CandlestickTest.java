package com.argentum.modelo;

import java.time.LocalDateTime;

import org.junit.Assert;
import org.junit.Test;

public class CandlestickTest {
	
	
	@Test(expected=IllegalArgumentException.class)
	public void maximoNaoDeveSerMenorDoQueMinimo(){
		
		LocalDateTime hoje = LocalDateTime.now();
		
		CandleBuilder builder = new CandleBuilder();
		
		Candlestick candle = builder.comAbertura(10.0).comFechamento(30.0)
									.comMinimo(25.0).comMaximo(15.0)
									.comVolume(200.0).comData(hoje)
									.geraCandle();	
	}
	
	@Test
	public void ehAltaSeFechamentoForIgualAbetura(){
		
		LocalDateTime hoje = LocalDateTime.now();
		
		CandleBuilder builder = new CandleBuilder();
		
		Candlestick candle = builder.comAbertura(30.0).comFechamento(30.0)
									.comMinimo(10.0).comMaximo(50.0)
									.comVolume(200.0).comData(hoje)
									.geraCandle();	
		
		Assert.assertTrue(candle.isAlta());
		
	}

}
