package solid.p01.coesao_SRP.cenario2.parte2;

public class GratificaoDeDezOuVintePorcento implements Gratificacao {

    @Override
    public double calcula(Funcionario funcionario) {

        if(funcionario.getSalarioBase() > 3000.0) {
            return funcionario.getSalarioBase() * 0.8;
        }
        else {
            return funcionario.getSalarioBase() * 0.9;
        }    }
}
