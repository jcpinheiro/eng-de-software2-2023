package dcomp.es2.locadora.modelo;

import dcomp.es2.locadora.builder.FilmeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FilmeTest {

    @Test
    void deveTerPrecoLocacaoPositivo() {

        Filme filme = FilmeBuilder
                .umFilme()
                .comPrecoDe(0.01d)
                .constroi();

        Assertions.assertEquals(0.01d, filme.getPrecoLocacao().doubleValue(), 0.00001 );

    }


    @Test
    void naoDeveTerPrecoLocacaoIgualAZero() {

        Filme filme = FilmeBuilder.umFilme().constroi();

        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> filme.setPrecoLocacao(0d) ,
                "O preço da locaçao deve ser maior do que Zero" );

    }

    @Test
    void naoDeveTerPrecoLocacaoNegativo() {

        Filme filme = FilmeBuilder.umFilme().constroi();


        Assertions.assertThrows( IllegalArgumentException.class,
                () -> filme.setPrecoLocacao(-0.01d),
                "O preço da locaçao deve ser maior do que Zero" );
    }

}