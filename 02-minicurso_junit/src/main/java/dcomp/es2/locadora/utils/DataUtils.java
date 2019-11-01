package dcomp.es2.locadora.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

public class DataUtils {
	
	public static LocalDate adicionarDias(LocalDate data, int dias) {
		return data.plusDays(dias);
	}
	
	public static LocalDate obterDataComDiferencaDias(int dias) {
		return LocalDate.now().plusDays(dias );
	}
	
	public static LocalDate amanha() {
		return LocalDate.now().plusDays(1);
	}
	
	public static LocalDate obterData(int dia, int mes, int ano){
		return LocalDate.of(ano, mes, dia);
	}
	
	public static boolean isMesmaData(LocalDate data1, LocalDate data2) {
		return data1.equals(data2);
	}
	

	public static boolean verificarDiaSemana(LocalDate data) {
		return data.getDayOfWeek() == DayOfWeek.MONDAY;
	}
}
