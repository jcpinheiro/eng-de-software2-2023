package solid.p01.coesao_SRP.cenario2.parte2;

public enum Cargo {

    DESENVOLVEDOR (new GratificaoDeDezOuVintePorcento()),
    DBA( new GratificaoDeQuinzeOuVinteCincoPorcento() ) ,
    TESTER(new GratificaoDeQuinzeOuVinteCincoPorcento());


    private Gratificacao gratificao;

    private Cargo(Gratificacao gratificao) {
        this.gratificao = gratificao;
    }

    public Gratificacao getGratificao() {
        return gratificao;
    }
}

