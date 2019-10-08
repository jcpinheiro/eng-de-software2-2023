package solid.p05.liskov.cenario1.parte1;

public interface DataRepository  {
	void persist(Object objeto);
	Object read(long id);
}
