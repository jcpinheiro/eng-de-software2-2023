package solid.p01.coesao_SRP.cenario1.parte5;

public class Divida {
	
	private double total;
	private String credor;
	private Cnpj cnpjCredor;
	
	private Pagamentos pagamentos= new Pagamentos();

	public Cnpj getCnpjCredor() {
		return this.cnpjCredor;
	}

	public void setCnpjCredor(Cnpj cnpjCredor) {
		this.cnpjCredor = cnpjCredor;
	}
	
	public String getCredor() {
		return this.credor;
	}

	public double getTotal() {
		return this.total;
	}

	public void setCredor(String credor) {
		this.credor = credor;
	}
	public void setTotal(double total) {
		this.total = total;
	}

	public double valorAPagar() {
		return this.total - this.pagamentos.getValorPago();
	}
}
