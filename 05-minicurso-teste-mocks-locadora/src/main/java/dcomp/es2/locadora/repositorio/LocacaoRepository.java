package dcomp.es2.locadora.repositorio;

import java.time.LocalDate;
import java.util.List;

import dcomp.es2.locadora.modelo.Locacao;

public interface LocacaoRepository {
	
	void salva(Locacao locacao);

	List<Locacao> emAtraso();

	List<Locacao> encerradasPorPeriodo(LocalDate dataInicio, LocalDate dataFim);
	
	
	
}
