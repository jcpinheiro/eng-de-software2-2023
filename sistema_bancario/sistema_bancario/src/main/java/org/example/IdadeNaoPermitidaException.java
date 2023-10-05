package org.example;

/**
 * Exce��o a ser lan�ada quando a idade de um poss�vel novo cliente n�o for aceita.
 * 
 * @author Gustavo Farias
 */
public class IdadeNaoPermitidaException extends RuntimeException {

	public static String MSG_IDADE_INVALIDA = "A idade do cliente precisa estar entre 18 e 65 anos.";
	
	public IdadeNaoPermitidaException(String msg) {
		super(msg);
	}

}
