package dcomp.es2.locadora.modelo;

import dcomp.es2.locadora.builder.FilmeBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class FilmeTest {
    private Filme filme;

    @BeforeEach
    public void setup() {
        filme = FilmeBuilder.umFilme().constroi();
    }

    @Test
    void deveTerPrecoLocacaoPositivo() {

        Filme filme =  FilmeBuilder.umFilme()
                .comPrecoDe(0.01)
                .constroi();


        assertEquals(0.01d, filme.getPrecoLocacao().doubleValue(), 0.00001 );

    }


    @Test
    void naoDeveTerPrecoLocacaoIgualAZero() {

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class,
                () -> filme.setPrecoLocacao(0d),
                "Para esse teste o preço da locação deve ser menor ou igual a zero");

        assertTrue(exception.getMessage().contains("O preço da locaçao deve ser maior do que Zero"));

    }

    @Test
    void naoDeveTerPrecoLocacaoNegativo() {

        assertThrows( IllegalArgumentException.class,
                () -> filme.setPrecoLocacao(-0.01d),
                "O preço da locaçao deve ser maior do que Zero" );
    }

}