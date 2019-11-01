package dcomp.es2.locadora.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Locacao {

	private Usuario usuario;
	
	private LocalDate dataLocacao;
	private LocalDate dataRetorno;
	private double valor;
	
	private Filme filme;
	private List<Filme> filmes = new ArrayList<>();
	
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public LocalDate getDataLocacao() {
		return dataLocacao;
	}
	
	public void setDataLocacao(LocalDate dataLocacao) {
		this.dataLocacao = dataLocacao;
	}
	
	public LocalDate getDataRetorno() {
		return dataRetorno;
	}
	
	public void setDataRetorno(LocalDate dataRetorno) {
		this.dataRetorno = dataRetorno;
	}
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}


	public Filme getFilme() {
		return filme;
	}

	public void setFilme(Filme filme) {
		this.filme = filme;
	}

	public List<Filme> getFilmes() {
		return filmes;
	}

	public void setFilmes(List<Filme> filmes) {
		this.filmes = filmes;
	}
}