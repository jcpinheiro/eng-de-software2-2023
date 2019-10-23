package solid.p05.liskov.cenario1.parte2;

public class CustomerRepository implements DataRepository  {

	private DataRepository dataRepository;

	@Override
	public void persist(Object objeto) {
		// TODO salva o objeto no banco de dados
		dataRepository.persist(objeto);
	}

	@Override
	public Object read(long id) {
		return dataRepository.read(id);
	}


}
