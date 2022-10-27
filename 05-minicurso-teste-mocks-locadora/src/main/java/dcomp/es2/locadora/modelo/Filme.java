package dcomp.es2.locadora.modelo;

import lombok.Data;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity

@Data
public class Filme {

	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String nome;
	private Integer estoque;
	private Double precoLocacao;  
	
	public Filme() {}
	
	public Filme(String nome, Integer estoque, Double precoLocacao) {
		if (precoLocacao < 0) {
			throw new IllegalArgumentException("Preço da locação não pode ser negativo");
		}
		
		this.nome = nome;
		this.estoque = estoque;
		this.precoLocacao = precoLocacao;
	}

	public void setPrecoLocacao(Double precoLocacao) {
		if (precoLocacao < 0) {
			throw new IllegalArgumentException("Preço da locação não pode ser negativo");
		}
		if (precoLocacao == 0) {
			throw new IllegalArgumentException("O preço da locaçao deve ser maior do que Zero");
		}
		this.precoLocacao = precoLocacao;
	}
}