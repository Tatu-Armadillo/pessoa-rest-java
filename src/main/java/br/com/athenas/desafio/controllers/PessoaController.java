package br.com.athenas.desafio.controllers;

import java.io.IOException;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.services.PessoaService;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @Autowired
    private PessoaService pessoaService;

    @GetMapping
    public ModelAndView listaPessoas() {
        ModelAndView modelAndView = new ModelAndView("read");
        modelAndView.addObject("pessoas", pessoaService.listaPessoas());
        return modelAndView;
    }

    //#region Cadastrar
    @GetMapping("/cadastrar")
    public ModelAndView cadastrar() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("pessoa", new Pessoa());
        return modelAndView;
    }

    @PostMapping("/cadastrar")
    public String cadastrar(@Valid Pessoa pessoa, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "create";
        }
        pessoaService.savePessoa(pessoa);
        return "redirect:/pessoas/";
    }
    //#endregion

    //#region Alterar

    //TODO Alterar ainda não está completo
    @GetMapping("/{id}/alterar")
    public ModelAndView altearar(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        modelAndView.addObject("pessoa", pessoaService.getPessoa(id));
        return modelAndView;
    }
    
    @PostMapping("/{id}/alterar/confirmar")
    public String confirmarAlteracao(@Valid Pessoa pessoa, BindingResult result) throws IOException {
        if (result.hasErrors()) {
            return "update";
        }
        pessoaService.updatePessoa(pessoa.getId(), pessoa);
        return "redirect:/pessoas/";
    }

    //#endregion

    //#region Excluir
    @GetMapping("/{id}/excluir")
    public ModelAndView excluir(@PathVariable Long id) {
        ModelAndView modelAndView = new ModelAndView("delete");
        modelAndView.addObject("pessoa", pessoaService.getPessoa(id));
        return modelAndView;
    }
    
    @GetMapping("/{id}/excluir/confirmar")
    public String confirmarExclusao(@PathVariable Long id) {
        pessoaService.deletePessoa(id);
        return "redirect:/pessoas/";
    }
    //#endregion


}
