package com.argentum.testes;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import com.argentum.modelo.Candlestick;
import com.argentum.modelo.CandlestickFactory;
import com.argentum.modelo.Negociacao;

public class TestaCandlestickFactory {
	
	public static void main(String[] args) {
		
		LocalDateTime hoje = LocalDateTime.now();
		
		Negociacao negociacao1 = new Negociacao(40.0, 100, hoje);
		Negociacao negociacao2 = new Negociacao(35.0, 100, hoje);
		Negociacao negociacao3 = new Negociacao(45.0, 100, hoje);
		Negociacao negociacao4 = new Negociacao(20.0, 100, hoje);
		
		List<Negociacao> negociacoes = Arrays.asList(negociacao1,negociacao2,negociacao3,negociacao4);
		
		CandlestickFactory fabrica = new CandlestickFactory();
		
		Candlestick candle = fabrica.geraCandleParaData(negociacoes, hoje);
		
		System.out.println(candle.getAbertura());
		System.out.println(candle.getFechamento());
		System.out.println(candle.getMaximo());
		System.out.println(candle.getMinimo());
		System.out.println(candle.getVolume());
		
	}

}
