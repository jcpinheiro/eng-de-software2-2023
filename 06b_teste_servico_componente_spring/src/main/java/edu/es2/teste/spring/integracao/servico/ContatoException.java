package edu.es2.teste.spring.integracao.servico;

public class ContatoException extends Exception {

	public ContatoException(Exception e) throws ContatoException {
		super(e);
	}

}
