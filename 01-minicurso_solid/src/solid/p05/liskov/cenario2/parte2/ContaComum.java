package solid.p05.liskov.cenario2.parte2;

public class ContaComum {

    private MovimentacaoConta movimentacaoConta = new MovimentacaoConta();

    public void deposita(double valor) {
        // aplicar um taxa
        movimentacaoConta.deposita(valor );
    }

    public void saca(double valor) {
        movimentacaoConta.saca(valor);
    }

    public double getSaldo() {
        return movimentacaoConta.getSaldo();
    }

    public void somaInvestimento() {
        movimentacaoConta.somaInvestimento();
    }
}