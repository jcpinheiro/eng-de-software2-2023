package org.example;

/**
 * Classe {@link ContaCorrente} que representa uma conta corrente real
 * e que poder� ser associada a um cliente.
 * @author Gustavo Farias
 */
public class ContaCorrente {
	
	private int id;
	
	private double saldo;
	
	private boolean ativa;

	public ContaCorrente(int id, double saldo, boolean ativa) {
		this.id = id;
		this.saldo = saldo;
		this.ativa = ativa;
	}

	/**
	 * M�todo que retorna o ID da conta corrente. 
	 * @return ID da conta corrente
	 */
	public int getId() {
		return id;
	}

	/**
	 * M�todo que atualiza o ID da conta corrente. 
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * M�todo que retorna o saldo da conta corrente. 
	 * @return saldo da conta corrente
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * M�todo que atualiza o saldo da conta corrente. 
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * M�todo que retorna o status da conta corrente, podendo ser Ativa ou Inativa. 
	 * @return status da conta corrente
	 */
	public boolean isAtiva() {
		return ativa;
	}

	/**
	 * M�todo que atualiza o status da conta corrente. 
	 */
	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}
	
	/**
	 * M�todo que retorna a representa��o textual de uma conta corrente. 
	 * @return representa��o textual da conta corrente
	 */
	@Override
	public String toString() {
		
		String str = "========================="
					+ "Id: " + this.id + "\n"
					+ "Saldo: " + this.saldo + "\n"
					+ "Status: " + (ativa?"Ativa":"Inativa") + "\n"
					+ "=========================";
		return str;
	}
	
}
