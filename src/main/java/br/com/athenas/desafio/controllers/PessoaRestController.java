package br.com.athenas.desafio.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.services.PessoaService;

@RestController
@RequestMapping("/api/pessoas")
public class PessoaRestController {
    
    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public List<Pessoa> listaPEssoas() {
        return pessoaService.listaPessoas();
    }

}
