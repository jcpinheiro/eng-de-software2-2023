import dcomp.es2.locadora.modelo.Usuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AssertivasTest {
	
	@Test
	public void test() {
		
		assertTrue(true);
		assertFalse(false);
		assertEquals(1, 1);
		assertEquals(Math.PI, 3.1415, 0.001);
		
		assertEquals("carro" , "carro");
		assertNotEquals("carro", "Carro");
		assertTrue("carro".equalsIgnoreCase("Carro") );
		
		Usuario usuario1 = new Usuario("Joao");
		Usuario usuario2 = new Usuario("Joao");
		Usuario usuario3 = usuario1;
		
		assertEquals(usuario1, usuario2);
		assertSame(usuario1, usuario3);
		assertNotNull(usuario2 );
		
		Usuario usuario4 = null;
		
		assertNull( usuario4 );
		
		assertEquals(1, 1, "Erro de Comparação");

	}
	
	
	

}
