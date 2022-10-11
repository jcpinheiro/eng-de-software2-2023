package dcomp.es2.locadora.servico;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import javax.transaction.Transactional;

import dcomp.es2.locadora.modelo.Filme;
import dcomp.es2.locadora.modelo.Locacao;
import dcomp.es2.locadora.modelo.Usuario;
import dcomp.es2.locadora.repositorio.LocacaoRepository;


public class LocacaoService {
	
	private LocacaoRepository locacaoRepository;
	private SPCService spcService;
	private EmailService emailService;
	
	public Locacao alugarFilme(Usuario usuario, Filme filme) {
		return this.alugarFilmes(usuario, Arrays.asList(filme ));
	}
	
	
	public Locacao alugarFilmes(Usuario usuario, List<Filme> filmes) {
		if (usuario == null) {
			throw new IllegalArgumentException("Usuário não pode ser nulo");
		}

		filmes.forEach(filme -> { 
			if (filme.getEstoque() == 0) 
				 throw new IllegalStateException("O filme " + filme.getNome() + "sem estoque.");
		});
		
	/*	if (spcService.estaNegativado(usuario) ) {
			throw new IllegalStateException("Não pode alugar filme para usuario com pendências no SPC");
		}*/
		
	   Locacao locacao = new Locacao();
	   locacao.setFilmes(filmes );
	   locacao.setUsuario(usuario);
	   locacao.setDataLocacao(LocalDate.now() );
	   
	   LocalDate dataRetorno = LocalDate.now().plusDays(1);
	   
	   if (dataRetorno.getDayOfWeek() == DayOfWeek.SUNDAY ) {
		   dataRetorno = dataRetorno.plusDays(1);
	   }
	   
	   locacao.setDataPrevista(dataRetorno );
	   
	   double valorTotal = calculaValorDaLocacao(filmes);

       locacao.setValor(valorTotal);
		
		
		//Salvando a locacao...	
        locacaoRepository.salva(locacao);
		
		return locacao;
	}

	
	private double calculaValorDaLocacao(List<Filme> filmes) {
		double valorTotal = 0d;
		   
		   for(int i=1; i <= filmes.size(); i++) {
			   double valorFilme = filmes.get(i-1).getPrecoLocacao();
			   
			   switch(i) {
			   
			   case 2: valorFilme = valorFilme * 0.90; break;
			   case 3: valorFilme = valorFilme * 0.70; break;
			   case 4: valorFilme = valorFilme * 0.50; break;
			   }
		 	   
			   valorTotal += valorFilme;
		   }
		return valorTotal;
	}
	
	
	public void notificaUsuariosEmAtraso() {

		List<Locacao> locacoesAtrasadas =  locacaoRepository.emAtraso();
		
		locacoesAtrasadas.forEach(locacao -> 
		              emailService.notifica(locacao.getUsuario() 
		));
	}
	
	
	public void setLocacaoRepository(LocacaoRepository locacaoRepository) {
		this.locacaoRepository = locacaoRepository;
	}
	
	
	public void setSpcService(SPCService spcService) {
		this.spcService = spcService;
	}
	
	
	public void setEmailService(EmailService emailService) {
		this.emailService = emailService;
	}
	
	
}