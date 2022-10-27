package dcomp.es2.locadora.repositorio;

import dcomp.es2.locadora.modelo.Locacao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LocacaoRepositoryFake implements LocacaoRepository {


	@Override
	public void salva(Locacao locacao) {

	}

	@Override
	public List<Locacao> emAtraso() {
		return new ArrayList<>();
	}

	@Override
	public List<Locacao> encerradasPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
		return new ArrayList<>();
	}
}