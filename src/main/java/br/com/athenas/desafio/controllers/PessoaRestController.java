package br.com.athenas.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.services.PessoaService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/person")
@Tag(name = "Person", description = "Endpoints for Persons")
public class PessoaRestController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaRestController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public ResponseEntity<Page<Pessoa>> listaPessoas(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction) {
        final var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));

        return ResponseEntity.ok(this.pessoaService.findAll(pageable));
    }

    @GetMapping("/findPersonsByName/{nome}")
    public ResponseEntity<Page<Pessoa>> findPersonsByName(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "12") Integer size,
            @RequestParam(value = "direction", defaultValue = "asc") String direction,
            @PathVariable(value = "nome") String nome) {
        final var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC : Direction.ASC;
        final Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, "nome"));
        
        return ResponseEntity.ok(this.pessoaService.findPersonsByName(pageable, nome));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> dateailsPessoa(@PathVariable Long id) {
        Pessoa pessoa = this.pessoaService.getPessoa(id);
        return ResponseEntity.ok(pessoa);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Pessoa> updatedPeso(@PathVariable Long id, @RequestBody Double peso) {
        Pessoa pessoa = this.pessoaService.updatedPeso(id, peso);
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/ideal/{id}")
    public String calcPesoIdeal(@PathVariable Long id) {
        return this.pessoaService.calcPesoIdeal(id);
    }

    @PostMapping
    @Transactional
    public Pessoa createPessoa(@RequestBody @Valid Pessoa pessoa) {
        Pessoa p = this.pessoaService.savePessoa(pessoa);
        return p;
    }

    @PutMapping("/{id}")
    @Transactional
    public Pessoa updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa p = this.pessoaService.updatePessoa(id, pessoa);
        return p;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removePessoa(@PathVariable Long id) {
        this.pessoaService.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
