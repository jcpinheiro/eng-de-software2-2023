package solid.p05.liskov.cenario2.parte2;

public class ContaUniversitaria  {

    private int milhas;
    private MovimentacaoConta movimentacaoConta = new MovimentacaoConta();


    public void deposita(double valor) {
        movimentacaoConta.deposita(valor );
        this.milhas += (int)valor;
    }

    public void saca(double valor) {
        movimentacaoConta.saca(valor);
    }

    public int getMilhas() {
        return milhas;
    }
    

}

