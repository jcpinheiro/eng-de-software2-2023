package dcomp.es2.locadora.repositorio;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dcomp.es2.locadora.builder.LocacaoBuilder;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.*;

public class LocacaoRepositoryTest {
	
	
	private EntityManager manager;
	private static EntityManagerFactory emf;
	private LocacaoRepository locacoes;
	private UsuarioRepository usuarios;

	@BeforeAll
	public static void inicio() {
		emf = Persistence.createEntityManagerFactory("locadoraPU_test");
	}
	
	@BeforeEach
	public void antes() {
		manager = emf.createEntityManager();
		manager.getTransaction().begin();
		
		locacoes = new LocacaoRepositoryImpl(manager ); 
		usuarios = new UsuarioRepositoryImpl(manager ); 
		
	}
	
	@AfterEach
	public void depois() {
		manager.getTransaction().rollback();
	}
	
	@AfterAll
	public static void fim() {
		emf.close();
	}

	
	@Test
	public void testSalva() { 	}


	@Test
    public void deveTrazerSomenteLocacoesAtrasadas() {


    	Usuario joao = new Usuario("Joao da Silva");
    	Usuario jose = new Usuario("Jose da Silva");
    	
		  	
		Locacao atrasada   = LocacaoBuilder.umaLocacao().paraUsuario(jose). emAtraso().constroi();
		Locacao emDia      = LocacaoBuilder.umaLocacao().paraUsuario(joao).constroi();
		Locacao finalizada = LocacaoBuilder.umaLocacao().paraUsuario(joao).jaFinalizada().constroi();
		
		locacoes.salva(atrasada );
		locacoes.salva(emDia );
		locacoes.salva(finalizada );

		manager.flush();
		manager.clear();
		
        List<Locacao> atrasadas = locacoes.emAtraso();

        Assertions.assertEquals(1, atrasadas.size());
        Assertions.assertEquals("Jose da Silva", atrasadas.get(0).getUsuario().getNome() );
    }
	
	
    @Test
    public void deveTrazerLocacoesEncerradosPorPeriodo() {

        // criando as datas
        
    	LocalDate dataInicio = LocalDate.now().minusDays(30);
    	LocalDate dataFim = LocalDate.now();
        
        Usuario joao = new Usuario("Joao da Silva");

        // criando as locações, cada um com uma data
        Locacao locacaoMuitoAntiga   = LocacaoBuilder.umaLocacao().paraUsuario(joao).comValor(3.0)
				                                                  .comDataLocacao(dataInicio.minusDays(5)).constroi();
		Locacao locacaoNoInicioDoPeriodo  = LocacaoBuilder.umaLocacao().paraUsuario(joao).comValor(4.0).comDataLocacao(dataInicio).constroi();
		Locacao locacaoNoFimDoPeriodo = LocacaoBuilder.umaLocacao().paraUsuario(joao).comValor(5.0).comDataLocacao(dataFim).constroi();
		Locacao locacaoNoPeriodo = LocacaoBuilder.umaLocacao().paraUsuario(joao).comValor(6.0).comDataLocacao(dataInicio.plusDays(5)).constroi();
		
		Locacao locacaoNoPeriodoEmAberto = LocacaoBuilder.umaLocacao().paraUsuario(joao).comValor(7.0).constroi();
       

        // persistindo os objetos no banco
        usuarios.salva(joao);
        locacoes.salva(locacaoMuitoAntiga);
        locacoes.salva(locacaoNoInicioDoPeriodo);
        locacoes.salva(locacaoNoFimDoPeriodo);
        locacoes.salva(locacaoNoPeriodo);
        locacoes.salva(locacaoNoPeriodoEmAberto);

        manager.flush();
        manager.clear();
        
        // invocando o metodo para testar
        List<Locacao> listaLocacoes = locacoes.encerradasPorPeriodo(dataInicio, dataFim);

        // garantindo que a query funcionou
        Assertions.assertEquals(3, listaLocacoes.size() );
       
        
        MatcherAssert.assertThat(4.0, CoreMatchers.is(listaLocacoes.get(0).getValor()) );
		MatcherAssert.assertThat(5.0, CoreMatchers.is(listaLocacoes.get(1).getValor()) );
		MatcherAssert.assertThat(6.0, CoreMatchers.is(listaLocacoes.get(2).getValor()) );

        
    }

}
