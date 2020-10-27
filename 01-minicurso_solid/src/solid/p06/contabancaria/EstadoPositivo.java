package solid.p06.contabancaria;

class EstadoPositivo implements EstadoConta {

      public void saca(Conta conta, double valor) {
        conta.saldo = conta.saldo - valor;

        if(conta.saldo < 0)
            conta.estado = new EstadoNegativo();

      }

      public void deposita(Conta conta, double valor) {

          conta.saldo += valor * 0.98;
      }

}
