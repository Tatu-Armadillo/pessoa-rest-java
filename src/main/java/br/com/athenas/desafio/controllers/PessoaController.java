package br.com.athenas.desafio.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.athenas.desafio.services.PessoaService;

@Controller
@RequestMapping("/pessoa")
public class PessoaController {

    private final PessoaService pessoaService;

    @Autowired
    public PessoaController(PessoaService pessoaService) {
        this.pessoaService = pessoaService;
    }

    @GetMapping
    public String viewPeople(final Model model) {
        model.addAttribute("people", pessoaService.findAll());
        return "viewpeople";
    }

}
