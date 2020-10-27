package solid.p06.contabancaria;

interface EstadoConta {

      void saca(Conta conta, double valor);
      void deposita(Conta conta, double valor);
    }