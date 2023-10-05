import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CalculadoraMockTest {

	@Test
	public void teste(){
		Calculadora calc = Mockito.spy(Calculadora.class);

		Mockito.when(calc.soma(2, 3)).thenReturn(5);
		//Mockito.when(calc.soma(Mockito.eq(2), Mockito.anyInt())).thenReturn(5);

		Assertions.assertEquals(5, calc.soma(2, 8));
	}
}