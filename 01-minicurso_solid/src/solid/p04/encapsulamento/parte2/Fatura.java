package solid.p04.encapsulamento.parte2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Fatura {

    private String cliente;
    private double total;
    private List<Pagamento> pagamentos;
    private boolean pago;

    public Fatura(String cliente, double valor) {
        this.cliente = cliente;
        this.total = valor;
        this.pagamentos = new ArrayList<Pagamento>();
        this.pago = false;
    }

    public String getCliente() {
        return cliente;
    }

    public double getTotal() {
        return total;
    }

    public List<Pagamento> getPagamentos() {
        return Collections.unmodifiableList(pagamentos );
    }

    public boolean isPago() {
        return pago;
    }

    public void setPago(boolean pago) {
        this.pago = pago;
    }

	public void realizaPagamento(Pagamento pagamento) {
		
		if (!isPago() ) {
			
			this.pagamentos.add(pagamento );
			if ( osPagamentosJaAtingiramOValorDaFatura() ) {
			   this.pago = true;	
			}
			
		}
	}

	private boolean osPagamentosJaAtingiramOValorDaFatura() {
		double total = 0;
		for (Pagamento pagamento : pagamentos) {
			total += pagamento.getValor();
		}
		return (this.total <= total );
	}


}

