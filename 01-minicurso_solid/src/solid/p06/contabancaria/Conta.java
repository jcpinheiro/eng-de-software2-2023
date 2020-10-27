package solid.p06.contabancaria;

public class Conta {

    protected double saldo;
    protected EstadoConta estado;

    public Conta() {
        estado = new EstadoPositivo();
    }

    public void saca(double valor) {
        estado.saca(this, valor);
    }

    public void deposita(double valor) {
        estado.deposita(this, valor);
    }
}