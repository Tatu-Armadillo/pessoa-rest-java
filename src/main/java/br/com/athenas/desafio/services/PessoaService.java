package br.com.athenas.desafio.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.athenas.desafio.models.Pessoa;
import br.com.athenas.desafio.repositories.PessoaRepository;

@Service
public class PessoaService {

    @Autowired
    private PessoaRepository pessoaRepository;

    public List<Pessoa> listaPessoas() {
        return pessoaRepository.findAll();
    }

    public Pessoa getPessoa(Long id) {
        Pessoa pessoa = pessoaRepository.findById(id).get();
        return pessoa;
    }

    public Pessoa savePessoa(Pessoa pessoa) {
        return pessoaRepository.save(pessoa);
    }

    public Pessoa updatePessoa(Long id, Pessoa pessoa) {
        Pessoa update = getPessoa(id);
        update.setNome(update.getNome().equals(pessoa.getNome()) ? update.getNome() : pessoa.getNome());
        update.setDataNasc(update.getDataNasc().equals(pessoa.getDataNasc()) ? update.getDataNasc() : pessoa.getDataNasc());
        update.setAltura(update.getAltura().equals(pessoa.getAltura()) ? update.getAltura() : pessoa.getAltura());
        update.setPeso(update.getPeso().equals(pessoa.getPeso()) ? update.getPeso() : pessoa.getPeso());
        update.setSexo(update.getSexo().equals(pessoa.getSexo()) ? update.getSexo() : pessoa.getSexo());
        return update;
    }

    public void deletePessoa(Long id) {
        pessoaRepository.deleteById(id);;
    }



}
