import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertivasTest {


    @Test
    public void testaAssertivas() {

        // Assertions.fail("Código falhou" );
        int soma = 2 + 3;

        assertEquals(5, soma );

        double pi = 3.14;
        assertEquals(3.14, pi, "");

        assertEquals(3.14159, Math.PI, 0.00001);


        assertTrue(true );
        assertFalse(false );

        assertNotEquals(4, 5);

        assertNull(null);

        assertNotNull(new Usuario("Joao") );




    }

}
