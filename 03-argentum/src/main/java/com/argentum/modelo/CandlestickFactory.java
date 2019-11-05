package com.argentum.modelo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CandlestickFactory {
	
	public Candlestick geraCandleParaData(List<Negociacao> negociacoes, LocalDateTime data){
		double abertura = negociacoes.isEmpty() ? 0 : negociacoes.get(0).getPreco();
		double fechamento = negociacoes.isEmpty() ? 0 :negociacoes.get(negociacoes.size()-1).getPreco();
		
		double minimo = negociacoes.isEmpty() ? 0 : negociacoes.get(0).getPreco();
		double maximo = negociacoes.isEmpty() ? 0 : negociacoes.get(0).getPreco();
		
		double volume = 0;
		for (Negociacao negociacao : negociacoes) {
			volume += negociacao.getVolume();
			
			if(negociacao.getPreco() > maximo){
				maximo = negociacao.getPreco();
			}else if ( negociacao.getPreco() < minimo){
				minimo = negociacao.getPreco();
			}
			
		}

		return new Candlestick(abertura, fechamento, maximo, minimo, volume, data);
	}

	public List<Candlestick> constroiCandles(List<Negociacao> negociacoes) {
		
		List<Candlestick> candlesticks = new ArrayList<>();
		
		List<Negociacao> negociacoesDoDia = new ArrayList<>();
		
		LocalDateTime dataAtual = negociacoes.get(0).getData();
				
		for (Negociacao negociacao : negociacoes) {
			
			if(negociacao.isMesmoDia(dataAtual)){
				negociacoesDoDia.add(negociacao);
			}else{
				Candlestick candle = geraCandleParaData(negociacoesDoDia, dataAtual);
				candlesticks.add(candle);
				
				negociacoesDoDia = new ArrayList<>();
				dataAtual = negociacao.getData();
			}
			
		}
		
		Candlestick candle = geraCandleParaData(negociacoesDoDia, dataAtual);
		candlesticks.add(candle);
		
		return candlesticks;
	}

}