package solid.p01.coesao_SRP.cenario1.parte5;

public class Cnpj {
	
	private String valor;
	
	public Cnpj(String valor) {
		this.valor = valor;
	}

	public String getValor() {
		return valor;
	}
	
	public boolean ehValido() {
		return primeiroDigitoVerificador() == primeiroDigitoCorreto()
				&& segundoDigitoVerificador() == segundoDigitoCorreto();
	}
	
	private int primeiroDigitoCorreto() {
		return 0;
	}

	private int primeiroDigitoVerificador() {
		return 0;
	}

	private int segundoDigitoCorreto() {
		return 0;
	}

	private int segundoDigitoVerificador() {
		return 0;
	}

}
