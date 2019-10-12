package solid.p05.liskov.cenario2.parte2;

class MovimentacaoConta {

    private double saldo;

    void deposita(double valor) {
        this.saldo += valor;
    }

    void saca(double valor) {
        if (valor <= this.saldo) {
            this.saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo Insuficiente");
        }
    }


    double getSaldo() {
        return saldo;
    }

    public void somaInvestimento(){
        this.saldo += this.saldo * 0.01;
    }

}
