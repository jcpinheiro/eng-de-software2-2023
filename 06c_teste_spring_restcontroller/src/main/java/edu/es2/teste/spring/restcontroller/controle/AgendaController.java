package edu.es2.teste.spring.restcontroller.controle;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import edu.es2.teste.spring.restcontroller.modelo.Contato;
import edu.es2.teste.spring.restcontroller.servico.ContatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;


@RestController
@RequestMapping("/agenda")
public class AgendaController {

	@Autowired
	private ContatoService contatoService;

	@GetMapping("/")
	public ResponseEntity<List<Contato>> contatos(){
		List<Contato> contatos = contatoService.buscarContatos();
		return ResponseEntity.ok(contatos);
	}

	@GetMapping("/{id}")
	public ResponseEntity<Contato> contato(@PathVariable Long id){
		return contatoService.buscarContato(id)
				.map(ResponseEntity::ok)
				.orElse(ResponseEntity.notFound().build());
	}

/*
	@PostMapping
	public ResponseEntity<Contato> inserir(@RequestBody @Valid Contato contato) throws URISyntaxException {
		contato = contatoService.inserirOuAlterar(contato);
		return new ResponseEntity(contato, HttpStatus.CREATED);
	}
*/

	@PostMapping
	public ResponseEntity<Contato> cadastro(@Valid @RequestBody Contato cliente,
											UriComponentsBuilder builder) {

		final Contato contatoSalvo = contatoService.inserirOuAlterar(cliente);
		final URI uri = builder
				.path("/agenda/{id}")
				.buildAndExpand(contatoSalvo.getId()).toUri();

		return ResponseEntity.created(uri).body(contatoSalvo );
	}

	@PutMapping ("/{id}")
	public ResponseEntity<Contato> alterar(@PathVariable Long id, @RequestBody @Valid Contato contato) throws URISyntaxException {

		if (contatoService.naoExisteContatoCom(id ) ) {
			return ResponseEntity.notFound().build();

		} else {
			contato.setId(id);
			Contato clienteAtualizado = contatoService.inserirOuAlterar(contato);
			return ResponseEntity.ok(clienteAtualizado );
		}
	}

	@DeleteMapping ("/{id}")
	public ResponseEntity<Contato> remover(@PathVariable Long id) {

		Optional<Contato> optional = contatoService.buscarContato(id );

		if (optional.isPresent()) {
			contatoService.remover(id );
			return ResponseEntity.noContent().build();
		}
		return ResponseEntity.notFound().build();
	}


}