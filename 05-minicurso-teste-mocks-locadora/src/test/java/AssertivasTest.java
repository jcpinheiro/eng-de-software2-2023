import org.junit.Assert;
import org.junit.Test;

import dcomp.es2.locadora.modelo.Usuario;

public class AssertivasTest {
	
	@Test
	public void test() {
		
		Assert.assertTrue(true);
		Assert.assertFalse(false);
		Assert.assertEquals(1, 1);
		Assert.assertEquals(Math.PI, 3.1415, 0.001);
		
		Assert.assertEquals("carro" , "carro");
		Assert.assertNotEquals("carro", "Carro");
		Assert.assertTrue("carro".equalsIgnoreCase("Carro") );
		
		Usuario usuario1 = new Usuario("Joao");
		Usuario usuario2 = new Usuario("Joao");
		Usuario usuario3 = usuario1;
		
		Assert.assertEquals(usuario1, usuario2);
		Assert.assertSame(usuario1, usuario3);
		Assert.assertNotNull(usuario2 ); 
		
		Usuario usuario4 = null;
		
		Assert.assertNull( usuario4 );
		
		Assert.assertEquals("Erro de comparação", 1, 1);
		Assert.assertEquals("Erro de comparação", 1, 1);

		
	}
	
	
	

}
