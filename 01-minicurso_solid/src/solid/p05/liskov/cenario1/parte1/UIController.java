package solid.p05.liskov.cenario1.parte1;

// Usado para interagir com o back-end da aplicação
public class UIController {
	
	private DataRepository dataRepository;
	
	public void persist(Object object) {
		
		if (!(dataRepository instanceof HistoricStockPriceRepository )) {
			dataRepository.persist(object);
		}
	}
	
	public void retrieve(long id) {
		dataRepository.read(id);
	}

}
