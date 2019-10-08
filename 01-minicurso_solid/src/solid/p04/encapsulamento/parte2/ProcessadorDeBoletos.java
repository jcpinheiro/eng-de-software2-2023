package solid.p04.encapsulamento.parte2;

public class ProcessadorDeBoletos {

    public void processa(Iterable<Boleto> boletos, Fatura fatura) {

        for(Boleto boleto : boletos) {
        	
            Pagamento pagamento = new Pagamento(boleto.getValor(), MeioDePagamento.BOLETO);
            fatura.realizaPagamento(pagamento );
        }

    }
}
