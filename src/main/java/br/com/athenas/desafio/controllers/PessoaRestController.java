package br.com.athenas.desafio.controllers;

import java.util.List;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.services.PessoaService;

@RestController
@RequestMapping
public class PessoaRestController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listaPessoas() {
        return pessoaService.listaPessoas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pessoa> dateailsPessoa(@PathVariable Long id) {
        Pessoa pessoa = pessoaService.getPessoa(id);
        if (pessoa == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pessoa);
    }

    @GetMapping("/imc/{id}")
    public String calcPesoIdeal(@PathVariable Long id) {
        return pessoaService.calcPesoIdeal(id);
    }

    @PostMapping
    @Transactional
    public Pessoa createPessoa(@RequestBody @Valid Pessoa pessoa) {
        Pessoa p = pessoaService.savePessoa(pessoa);
        return p;
    }

    @PutMapping("/{id}")
    @Transactional
    public Pessoa updatePessoa(@PathVariable Long id, @RequestBody Pessoa pessoa) {
        Pessoa p = pessoaService.updatePessoa(id, pessoa);
        return p;
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<?> removePessoa(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return ResponseEntity.ok().build();
    }

}
