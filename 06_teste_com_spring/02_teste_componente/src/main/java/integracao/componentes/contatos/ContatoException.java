package integracao.componentes.contatos;

public class ContatoException extends Exception {

	public ContatoException(Exception e) throws ContatoException {
		super(e);
	}

}
