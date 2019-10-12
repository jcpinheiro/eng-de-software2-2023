package solid.p02.acoplamento.parte2;

import java.util.List;

public class GeradorDeNotaFiscal {


    private List<AcaoAposGerarNota> acoesAposGerarNota;

    public GeradorDeNotaFiscal(List<AcaoAposGerarNota> acoes ) {
        this.acoesAposGerarNota = acoes;
    }


    public NotaFiscal gera(Fatura fatura) {

        double valor = fatura.getValorMensal();

        NotaFiscal nf = new NotaFiscal(valor, impostoSimplesSobreO(valor) );

        acoesAposGerarNota.forEach(acaoAposGerarNota -> acaoAposGerarNota.executa(nf) );


        return nf;
    }

    private double impostoSimplesSobreO(double valor) {
        return valor * 0.06;
    }
}
