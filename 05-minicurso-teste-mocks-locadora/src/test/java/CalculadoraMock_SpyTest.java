import dcomp.es2.locadora.servico.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;


public class CalculadoraMock_SpyTest {
	
	@Mock
	private Calculadora calcMock;
	
	@Spy
	private Calculadora calcSpy;
	

	@BeforeEach
	public void setup(){
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void devoMostrarDiferencaEntreMockSpy() {

		Mockito.when(calcMock.soma(1, 2)).thenReturn(5);

		Mockito.when(calcSpy.soma(1, 2)).thenReturn(5);
		//Mockito.doReturn(5).when(calcSpy).soma(1, 2);

		System.out.println("Mock:" + calcMock.soma(1, 2));
		System.out.println("Spy:" + calcSpy.soma(1, 2));

		System.out.println(" ------ ");

		System.out.println("Mock:" + calcMock.soma(1, 3));
		System.out.println("Spy:" + calcSpy.soma(1, 3));
		
		System.out.println("---- Mock -----");
		calcMock.imprime();

		Mockito.doNothing().when(calcSpy).imprime();
		System.out.println("--- Spy ---");
		calcSpy.imprime();
	}
	
	
	

}