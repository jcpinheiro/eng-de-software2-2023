package dcomp.es2.locadora.modelo;

import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;


@Entity
public @Data class Locacao {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(cascade= {CascadeType.MERGE, CascadeType.PERSIST})
	private Usuario usuario;
	
	private LocalDate dataLocacao;
	private LocalDate dataPrevista;
	
	private LocalDate dataDevolucao;

	private double valor;
	
	@ManyToMany(cascade=CascadeType.ALL)
	private List<Filme> filmes = new ArrayList<>();
	

	public List<Filme> getFilmes() {
		return filmes;
	}


}