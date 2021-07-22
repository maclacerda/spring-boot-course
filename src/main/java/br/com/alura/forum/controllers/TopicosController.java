package br.com.alura.forum.controllers;

import java.net.URI;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.alura.forum.controllers.dtos.DetalheTopicoDTO;
import br.com.alura.forum.controllers.dtos.TopicoDTO;
import br.com.alura.forum.forms.AtualizarTopicoForm;
import br.com.alura.forum.forms.TopicoForm;
import br.com.alura.forum.modelo.Topico;
import br.com.alura.forum.repositories.CursoRepository;
import br.com.alura.forum.repositories.TopicoRepository;

@RestController
@RequestMapping("/topicos")
public class TopicosController {

	@Autowired
	private TopicoRepository repository;

	@Autowired
	private CursoRepository cursoRepository;

	@GetMapping
	@Cacheable(value = "listaTopicos")
	public Page<TopicoDTO> listaTopicos(@RequestParam(required = false) String nomeCurso, @PageableDefault(direction = Direction.DESC, sort = "id", page = 0, size = 10) Pageable paginacao) {
		Page<Topico> topicos;

		if (nomeCurso == null) {
			topicos = repository.findAll(paginacao);
		} else {
			topicos = repository.findByCursoNome(nomeCurso, paginacao);
		}

		return TopicoDTO.converter(topicos);
	}

	// Caso o nome do parametro seja diferente da parte "dinamica" da URL
	// basta fazer: @PathVariable("id")
	@GetMapping("/{id}")
	public DetalheTopicoDTO detalhar(@PathVariable Long id) {
		Topico topico = repository.getOne(id);

		return new DetalheTopicoDTO(topico);
	}

	@PostMapping
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> cadastrar(@RequestBody @Valid TopicoForm form, UriComponentsBuilder builder) {
		Topico topico = form.converter(cursoRepository);
		repository.save(topico);

		URI uri = builder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();

		return ResponseEntity.created(uri).body(new TopicoDTO(topico));
	}

	@PutMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<TopicoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AtualizarTopicoForm form) {
		Topico topico = form.atualizar(id, repository);

		return ResponseEntity.ok(new TopicoDTO(topico));
	}

	@DeleteMapping("/{id}")
	@Transactional
	@CacheEvict(value = "listaTopicos", allEntries = true)
	public ResponseEntity<Void> remover(@PathVariable Long id) {
		repository.deleteById(id);

		return ResponseEntity.ok().build();
	}

}
