package solid.p06.contabancaria;

class EstadoNegativo implements EstadoConta {

    public void saca(Conta conta, double valor) {
        throw new RuntimeException("Não é possível sacar!. Conta Negativa");
      }


    public void deposita(Conta conta, double valor) {
        conta.saldo = conta.saldo + valor * 0.95;

        if(conta.saldo > 0)
            conta.estado = new EstadoPositivo();
      }

    }