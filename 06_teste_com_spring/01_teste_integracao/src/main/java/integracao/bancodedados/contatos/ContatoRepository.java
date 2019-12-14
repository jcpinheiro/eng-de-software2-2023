package integracao.bancodedados.contatos;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {

    Contato findFirstByNome(String chefe);

    @Query(value = "From Contato")
    List<Contato> todos(Sort sort );
}
